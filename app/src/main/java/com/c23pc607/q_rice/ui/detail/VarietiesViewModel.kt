package com.c23pc607.q_rice.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c23pc607.q_rice.data.remote.response.VarietiesResponse
import com.c23pc607.q_rice.data.retrofit.ApiService

class VarietiesViewModel : ViewModel() {

    private val _varieties = MutableLiveData<VarietiesResponse>()
    val varieties: LiveData<VarietiesResponse> = _varieties

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "VarietiesViewModel"
    }

    suspend fun getVarieties(id: String) {
        _isLoading.value = true
        val response = ApiService.getApi()?.getVarieties(id)
        try {
            _isLoading.value = false
            if (response?.isSuccessful == true) {
                _varieties.value = response.body()
            } else {
                Log.e(TAG, "onFailure: ${response?.message()}")
            }
        }

        catch (e: Exception) {
            _isLoading.value = false
            Log.e(TAG, "onFailure: ${e.message.toString()}")
        }
    }
}