package com.application.koin.data.database

import androidx.room.Dao
import com.application.koin.data.model.Configurations
import com.application.koin.data.model.Photos
import com.application.koin.data.model.Users
import com.application.prueba.data.database.BaseDao

@Dao
abstract class ConfigurationDao: BaseDao<Configurations>(Configurations::class.java)

@Dao
abstract class UserDao: BaseDao<Users>(Users::class.java)

@Dao
abstract class PhotoDao: BaseDao<Photos>(Photos::class.java)