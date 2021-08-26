package com.application.koin.data.database

import com.application.prueba.app.providers.DatabaseProvider

class RoomDataSource(private val db: DatabaseProvider): LocalDataSource {
    override fun configuration(): ConfigurationDao = db.configurationDao
    override fun user(): UserDao = db.userDao
    override fun photo(): PhotoDao = db.photoDao
}

interface LocalDataSource {
    fun configuration(): ConfigurationDao
    fun user(): UserDao
    fun photo() : PhotoDao
}