package com.application.prueba.app.providers

import com.application.koin.data.database.LocalDataSource
import com.application.koin.data.database.RoomDataSource
import com.application.prueba.data.repository.ConfigurationRepository
import com.application.prueba.iu.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module
import provideRemoteDataSource

val dataStoreModule  = module {
        single { provideDataStoreInstance(androidContext()) }
        single { DataStoreProvider(get()) }
}

val remoteDataSourceModule = module {
        single { provideRemoteDataSource(get()) }
}

val dataBaseModule = module {
        single { provideDatabase(androidApplication()) }
        single { provideConfigurationDao(get()) }
        single { provideUserDao(get()) }
        single { providePhotoDao(get()) }
        single { RoomDataSource(get()) }binds arrayOf(LocalDataSource::class)

}

val repositoryModule = module {
        single { ConfigurationRepository(get()) }
}

val viewModelModule = module{

        single { HomeViewModel(get()) }

}
