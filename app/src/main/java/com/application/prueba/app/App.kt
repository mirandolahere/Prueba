package com.application.prueba.app

import android.app.Application
import com.application.prueba.app.providers.*
import com.application.prueba.app.providers.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(
                dataStoreModule , remoteDataSourceModule ,
                dataBaseModule, repositoryModule, viewModelModule,
            ))

        }

    }
}