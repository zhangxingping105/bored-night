package com.zxp.boredom

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var textViewMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        textViewMessage = findViewById(R.id.textViewMessage)

        loginButton.setOnClickListener {
            val accountValue = username.text.toString()
            val passwordValue = password.text.toString()
            if (accountValue.isNullOrBlank() || passwordValue.isNullOrBlank()) {
                textViewMessage.text = "用户名密码不能为空"
            } else {
                login(username.text.toString(), password.text.toString())
            }
        }
        val editTextPassword = findViewById<EditText>(R.id.password)
        val imageViewToggle = findViewById<ImageView>(R.id.imageViewToggle)

        var isPasswordVisible = false
        imageViewToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            editTextPassword.inputType = if (isPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            editTextPassword.setSelection(editTextPassword.text.length)

            // 切换图标
            imageViewToggle.setImageResource(if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off)
        }
    }

    private fun login(account: String, password: String) {
        val request = LoginRequest(account, password)
        val baseUrl = this.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://$baseUrl")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = apiService.login(request)
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, FriendListActivity::class.java)
                    intent.putExtra("account", account)
                    TokenManager.setToken(response.body()?.data)
                    startActivity(intent)
                    finish()
                } else {
                    // 处理登录失败
                    textViewMessage.text = "用户名或密码错误"
                }
            } catch (e: Exception) {
                e.printStackTrace();
                // 处理异常
                textViewMessage.text = "登录失败"
            }
        }
    }
}

data class LoginRequest(val account: String, val password: String)

data class ApiResponse<T>(val code: Int, val message: String, val data: T?)

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<ApiResponse<String>>
}

