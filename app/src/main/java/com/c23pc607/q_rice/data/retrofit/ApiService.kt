package com.c23pc607.q_rice.data.retrofit

import com.c23pc607.q_rice.data.remote.request.LoginRequest
import com.c23pc607.q_rice.data.remote.response.*
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

    @GET("api/rice-varieties/{id}")
    suspend fun getVarieties(
            @Path("id") id: String
    ): Response<VarietiesResponse>

    @GET("api/rice-diseases/{id}")
    suspend fun getDiseases(
            @Path("id") id: String
    ): Response<DiseasesResponse>

    @GET("api/nutrient-deficiencies/{id}")
    suspend fun getDeficiencies(
            @Path("id") id: String
    ): Response<DeficienciesResponse>

    companion object {
        fun getApi(): ApiService? {
            return ApiConfig.getApiService?.create(ApiService::class.java)
        }
    }
}