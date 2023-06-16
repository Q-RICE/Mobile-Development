package com.c23pc607.q_rice.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c23pc607.q_rice.data.remote.response.DiseasesResponse
import com.c23pc607.q_rice.data.retrofit.ApiService

class DiseasesViewModel : ViewModel() {

    private val _diseases = MutableLiveData<DiseasesResponse>()
    val diseases: LiveData<DiseasesResponse> = _diseases

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DiseasesViewModel"
    }

    suspend fun getDiseases(id: String) {
        _isLoading.value = true
        val response = ApiService.getApi()?.getDiseases(id)
        try {
            _isLoading.value = false
            if (response?.isSuccessful == true) {
                _diseases.value = response.body()
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