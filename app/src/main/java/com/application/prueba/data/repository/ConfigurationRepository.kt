package com.application.prueba.data.repository


import com.application.prueba.data.network.SafeApiCall
import com.application.prueba.data.network.ServiceApi

class ConfigurationRepository(private val api: ServiceApi/*, private val configurationDao: ConfigurationDao*/) : SafeApiCall{

    suspend fun login(user: String , password : String) = safeApiCall{
        api.login(user)
    }


}