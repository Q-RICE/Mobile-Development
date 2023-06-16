package com.c23pc607.q_rice.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    var email: String,
    @SerializedName("password")
    var password: String
)