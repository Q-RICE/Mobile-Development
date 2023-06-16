package com.c23pc607.q_rice.ui.register

import androidx.lifecycle.*
import com.c23pc607.q_rice.data.remote.response.LoginResponse
import com.c23pc607.q_rice.ui.utils.Event

class RegisterViewModel : ViewModel() {

    private val _user = MutableLiveData<LoginResponse>()
    val user: LiveData<LoginResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "RegisterViewModel"
    }

    /*fun postRegister(username: String, email: String, password: String) {
        _isLoading.value = true
        val request = LoginRequest()
        request.username = username
        request.email = email
        request.password = password

        val client = ApiConfig().getApiService().create(ApiService::class.java)
        client.postRegister(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    var user = _user.value
                    user = response.body()
//                    _snackbarText.value = Event(response.body()?.message.toString())
                    Log.e("token", user!!.user?.accessToken.toString())
                    Log.e("username", user!!.user?.username.toString())
                    Log.e("email", user!!.user?.email.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "Response: ${response.toString()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }*/
}