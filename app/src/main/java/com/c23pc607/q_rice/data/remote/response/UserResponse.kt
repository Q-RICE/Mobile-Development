package com.c23pc607.q_rice.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
