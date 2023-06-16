package com.c23pc607.q_rice.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.c23pc607.q_rice.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        initAction()
    }

    /*fun initAction() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    fun register() {
        val request = LoginRequest()
        request.username = binding.etUsername.text.toString().trim()
        request.email = binding.etEmail.text.toString().trim()
        request.password = binding.etPassword.text.toString().trim()

        val retro = ApiConfig().getApiService().create(ApiService::class.java)
        Log.d("RegisterActivity", "Before enqueue")
        retro.postRegister(request).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("RegisterActivity", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val responseBody = response.body().toString()
                    Log.d("RegisterActivity", "Response body: $responseBody")
                    Log.d("RegisterActivity", "Username: ${userResponse?.user?.username}")
                    Log.d("RegisterActivity", "Email: ${userResponse?.user?.email}")
                    Log.d("RegisterActivity", "Access Token: ${userResponse?.user?.accessToken}")
                } else {
                    Log.e("RegisterActivity", "Registration failed: ${response.code()}")
                }
            }
        })
    }*/
}