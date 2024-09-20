package com.zxp.boredom

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zxp.boredom.ui.theme.BoredomTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 启动登录界面
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // 关闭当前活动
        finish()
    }
//    private lateinit var editTextUsername: EditText
//    private lateinit var editTextPassword: EditText
//    private lateinit var buttonLogin: Button
//    private lateinit var textViewMessage: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        editTextUsername = findViewById(R.id.editTextUsername)
//        editTextPassword = findViewById(R.id.editTextPassword)
//        buttonLogin = findViewById(R.id.buttonLogin)
//        textViewMessage = findViewById(R.id.textViewMessage)
//
//        buttonLogin.setOnClickListener { login(editTextUsername.text.toString(), editTextPassword.text.toString()) }
//    }
//    private fun login(account: String, password: String) {
//        val url = "http://127.0.0.1:8090/api/auth/login"
//        val client = OkHttpClient()
//
//        val requestBody = FormBody.Builder()
//            .add("account", account)
//            .add("password", password)
//            .build()
//
//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    startActivity(Intent(this@LoginActivity, ChatActivity::class.java))
//                    finish()
//                } else {
//                    // 处理登录失败
//                    textViewMessage.text = "用户名或密码错误"
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                // 处理请求失败
//            }
//        })
//    }
//}
//
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BoredomTheme {
//        Greeting("Android")
//    }
}