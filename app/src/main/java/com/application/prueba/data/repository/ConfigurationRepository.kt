package com.application.prueba.data.repository


import com.application.koin.data.database.ConfigurationDao
import com.application.koin.data.database.PhotoDao
import com.application.koin.data.model.Photos
import com.application.prueba.data.network.SafeApiCall
import com.application.prueba.data.network.ServiceApi

class ConfigurationRepository( private val photoDao: PhotoDao) : SafeApiCall{

    suspend fun login(user: String , password : String) = safeApiCall{
        //api.login(user)
    }
    suspend fun photo (photo:Photos) = safeApiCall {
        photoDao.insert(photo)
    }
    suspend fun list () = safeApiCall {
        photoDao.getAll()
    }
    suspend fun listById (id:Int) = safeApiCall {
        photoDao.getById(id)
    }

    suspend fun deleteById (id:Int) = safeApiCall {
        photoDao.deleteById(id)
    }
}