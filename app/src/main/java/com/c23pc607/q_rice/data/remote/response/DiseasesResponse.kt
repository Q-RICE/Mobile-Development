package com.c23pc607.q_rice.data.remote.response

import com.google.gson.annotations.SerializedName

data class DiseasesResponse(

	@field:SerializedName("symptoms")
	val symptoms: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
