package com.application.prueba.app.providers

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreProvider  = module {
        single { provideDataStoreInstance(androidContext()) }
        single { DataStoreProvider(get()) }
}

val remoteDataSource = module {
        single { provideDataStoreInstance(get()) }
}
