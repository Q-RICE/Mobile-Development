package com.c23pc607.q_rice.data.retrofit

import com.c23pc607.q_rice.data.remote.request.LoginRequest
import com.c23pc607.q_rice.data.remote.response.LoginResponse
import com.c23pc607.q_rice.data.remote.response.ServiceResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Multipart
    @POST("api/predict")
    suspend fun postPredict(
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Part("model") model: RequestBody
    ): Response<ServiceResponse>

    companion object {
        fun getApi(): ApiService? {
            return ApiConfig.getApiService?.create(ApiService::class.java)
        }
    }
}