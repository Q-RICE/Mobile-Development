package com.c23pc607.q_rice.data.remote.response

import com.google.gson.annotations.SerializedName

data class ServiceResponse(

	@field:SerializedName("result")
	val result: Result,

	@field:SerializedName("success")
	val success: Boolean
)

data class Result(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("imageFilename")
	val imageFilename: String,

	@field:SerializedName("predictionResult")
	val predictionResult: String,

	@field:SerializedName("rice_variety_id")
	val riceVarietyId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
