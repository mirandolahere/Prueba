package com.application.prueba.app.providers

import com.application.koin.data.database.LocalDataSource
import com.application.koin.data.database.RoomDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module
import kotlin.math.sin

val dataStoreModule  = module {
        single { provideDataStoreInstance(androidContext()) }
        single { DataStoreProvider(get()) }
}

val remoteDataSourceModule = module {
        single { provideDataStoreInstance(get()) }
}

val dataBaseModule = module {
        single { provideDatabase(androidApplication()) }
        single { providePhotoDao(get()) }
        single { RoomDataSource(get()) }binds arrayOf(LocalDataSource::class)

}

