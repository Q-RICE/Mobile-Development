package com.c23pc607.q_rice.data

import com.c23pc607.q_rice.data.retrofit.ApiService
import com.c23pc607.q_rice.data.remote.request.LoginRequest
import com.c23pc607.q_rice.data.remote.response.LoginResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
      return  ApiService.getApi()?.loginUser(loginRequest = loginRequest)
    }
}