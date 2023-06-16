package com.c23pc607.q_rice.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c23pc607.q_rice.data.remote.response.DeficienciesResponse
import com.c23pc607.q_rice.data.retrofit.ApiService

class DeficienciesViewModel : ViewModel() {

    private val _deficiencies = MutableLiveData<DeficienciesResponse>()
    val deficiencies: LiveData<DeficienciesResponse> = _deficiencies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DeficienciesViewModel"
    }

    suspend fun getDeficiencies(id: String) {
        _isLoading.value = true
        val response = ApiService.getApi()?.getDeficiencies(id)
        try {
            _isLoading.value = false
            if (response?.isSuccessful == true) {
                _deficiencies.value = response.body()
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