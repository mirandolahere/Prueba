package com.application.prueba.app.providers

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.koin.data.database.ConfigurationDao
import com.application.koin.data.database.PhotoDao
import com.application.koin.data.database.UserDao
import com.application.koin.data.model.Configurations
import com.application.koin.data.model.Photos
import com.application.koin.data.model.Users

@Database(
    version = 1, exportSchema = false,
    entities = [
        Configurations::class,
        Users::class,
        Photos::class
    ],
)
abstract class DatabaseProvider : RoomDatabase() {
    abstract val configurationDao: ConfigurationDao
    abstract val userDao: UserDao
    abstract val photoDao : PhotoDao

}

fun provideConfigurationDao(database: DatabaseProvider) : ConfigurationDao {
    return database.configurationDao
}

fun provideUserDao(database: DatabaseProvider) : UserDao {
    return database.userDao
}

fun providePhotoDao(database: DatabaseProvider) : PhotoDao{
    return database.photoDao
}
fun provideDatabase(application: Application) : DatabaseProvider {
    return Room.databaseBuilder(application, DatabaseProvider::class.java, "ac.database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}