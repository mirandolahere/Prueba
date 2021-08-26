import com.application.prueba.app.providers.DataStoreProvider
import com.application.prueba.data.network.ServiceApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideRemoteDataSource(dataStoreProvider: DataStoreProvider) = RemoteDataSourceProvider.buildApi(ServiceApi::class.java, dataStoreProvider)

object RemoteDataSourceProvider {
    fun <Api> buildApi(
        api: Class<Api>,
        dataStoreProvider: DataStoreProvider
    ): Api {

        return Retrofit.Builder()
            .baseUrl("https://flutterweb-admin.herokuapp.com")
            .client(getRetrofitClient(dataStoreProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getRetrofitClient(dataStoreProvider: DataStoreProvider): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    val accessToken = runBlocking {
                        dataStoreProvider.accessToken.first()
                    }
                    it.addHeader("Accept", "application/json")
                    it.addHeader("x-token", accessToken)
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    client
                        .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }.build()
    }
}