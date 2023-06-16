package com.c23pc607.q_rice.ui.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c23pc607.q_rice.data.remote.response.ServiceResponse
import com.c23pc607.q_rice.data.retrofit.ApiService
import com.c23pc607.q_rice.ui.utils.Event
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ServiceViewModel : ViewModel() {

    private val _result = MutableLiveData<com.c23pc607.q_rice.data.remote.response.Result>()
    val result: LiveData<com.c23pc607.q_rice.data.remote.response.Result> = _result

    private val _service = MutableLiveData<ServiceResponse>()
    val service: LiveData<ServiceResponse> = _service

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "ServiceViewModel"
    }

    suspend fun postPredict(token: String, imageFile: File, model: String) {
        _isLoading.value = true

        if (!imageFile.exists()) {
            Log.e(TAG, "File does not exist: ${imageFile.path}")
            // Handle the error condition when the file does not exist
            return
        }

        Log.d(TAG, "File exists: ${imageFile.path}")

        if (!imageFile.canRead()) {
            Log.e(TAG, "File cannot be read: ${imageFile.path}")
            // Handle the error condition when the file cannot be read
            return
        }

        Log.d(TAG, "File can be read: ${imageFile.path}")

        // The file exists and is readable, continue with the API call
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        val modelPart = model.toRequestBody("text/plain".toMediaTypeOrNull())

        try {
            val response = ApiService.getApi()?.postPredict("Bearer $token", imagePart, modelPart)
            if (response?.isSuccessful == true) {
                _service.value = response.body()
                _result.value = response.body()?.result
                _snackbarText.value = Event(response.body()?.success.toString())
                Log.d(TAG, "Success: ${response.body()?.result}")
            } else {
                Log.e(TAG, "onFailure: ${response?.message()}")
                // Handle error response
                val errorBody = response?.errorBody()?.string()
                val errorMessage = "Server Error: ${response?.code()} - $errorBody"
                Log.e(TAG, errorMessage)
                // You can show an error message to the user or handle the error in any way you prefer
            }
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
        }

        _isLoading.value = false
    }
}