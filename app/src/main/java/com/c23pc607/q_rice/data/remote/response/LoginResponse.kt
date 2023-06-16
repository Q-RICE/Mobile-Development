package com.c23pc607.q_rice.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    var token: String
)