package com.c23pc607.q_rice.data.remote.response

sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
    data class Error(val msg: String?) : BaseResponse<Nothing>()
}