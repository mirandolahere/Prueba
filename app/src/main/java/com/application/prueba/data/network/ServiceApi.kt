package com.application.prueba.data.network

import retrofit2.http.Body
import retrofit2.http.POST

const val API_VERSION = "/api"

interface ServiceApi {
        @POST("$API_VERSION/auth/login")
        suspend fun login(@Body loginRequest: String): String
}
