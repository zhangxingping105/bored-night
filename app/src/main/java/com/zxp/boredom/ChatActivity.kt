package com.zxp.boredom

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class ChatActivity : AppCompatActivity(){
    private lateinit var messageContainer: LinearLayout
    private lateinit var editText: EditText
    private lateinit var sendButton: Button
    private lateinit var messageArea: ScrollView
    private lateinit var webSocketClient: WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageArea = findViewById(R.id.message_area)
        messageContainer = findViewById(R.id.message_container)
        editText = findViewById(R.id.edit_text)
        sendButton = findViewById(R.id.button_send)
        // 获取用户名
        var account = intent.getStringExtra("account")
        setupWebSocket(account)

        sendButton.setOnClickListener {
            sendMessage(account, editText.text.toString())
        }
    }

    private fun setupWebSocket(account: String?) {
        val uri = URI("ws://10.0.2.2:8090/chat?account=$account") // WebSocket 地址
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                runOnUiThread {
                    // 连接成功
                }
            }

            override fun onMessage(message: String?) {
                runOnUiThread {
                    if (message != null) {
                        val messageJson = Json.parseToJsonElement(message).jsonObject
                        val messageContent = messageJson["content"]?.jsonPrimitive?.content ?: ""
                        displayMessage(messageContent, false)
                    } else {
                        displayMessage("未知消息", false)
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                runOnUiThread {
                    // 连接关闭
                }
            }

            override fun onError(ex: Exception?) {
                runOnUiThread {
                    // 处理错误
                }
            }
        }
        webSocketClient.connect()
    }

    private fun sendMessage(account: String?, messageText: String) {
        if (messageText.isNotBlank()) {
            webSocketClient.send(messageText)
            displayMessage(messageText, true)
            editText.text.clear()
        }
    }

    private fun displayMessage(messageText: String, isOwnMessage: Boolean) {
        val messageView = TextView(this)
        messageView.text = messageText
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // params.gravity = if (isOwnMessage) Gravity.END else Gravity.START
        if (isOwnMessage) {
            params.gravity = Gravity.END // 自己的消息靠右
            messageView.setBackgroundResource(R.drawable.sender_message_background) // 设置背景
        } else {
            params.gravity = Gravity.START // 对方的消息靠左
            messageView.setBackgroundResource(R.drawable.receiver_message_background) // 设置背景
        }
        messageView.layoutParams = params
        messageContainer.addView(messageView)

        messageArea.post { messageArea.fullScroll(View.FOCUS_DOWN) }
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClient.close()
    }
}