package com.zxp.boredom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * 好友列表
 *
 * @description: 好友列表
 * @author: zxp
 * @date: 2024/9/21 8:45
 */
data class FriendDetail(val friendAccount: String, val username: String)


interface FriendService {
    @GET("/api/friend")
    fun getFriends(): Call<ApiResponse<List<FriendDetail>>>
}

class FriendListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendAdapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var account = intent.getStringExtra("account")
        val baseUrl = this.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://${baseUrl}")
            .client(OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(account.toString()))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FriendService::class.java)
        service.getFriends().enqueue(object : Callback<ApiResponse<List<FriendDetail>>> {

            override fun onResponse(
                call: Call<ApiResponse<List<FriendDetail>>>,
                response: retrofit2.Response<ApiResponse<List<FriendDetail>>>
            ) {
                if (response.isSuccessful) {
                    val friends = response.body()?.data ?: emptyList()
                    friendAdapter = FriendAdapter(friends) { friend ->
                        showChatBox(account.toString(), friend)
                    }
                    recyclerView.adapter = friendAdapter
                } else {
                    Toast.makeText(this@FriendListActivity, "获取好友列表失败", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<FriendDetail>>>, t: Throwable) {
                Toast.makeText(this@FriendListActivity, "获取好友列表失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showChatBox(account: String, friend: FriendDetail) {
//        AlertDialog.Builder(this)
//            .setTitle("聊天")
//            .setMessage("与 ${friend.username} 聊天")
//            .setPositiveButton("确定") { dialog, _ -> dialog.dismiss() }
//            .show()
        val intent = Intent(this@FriendListActivity, ChatActivity::class.java)
        intent.putExtra("account", account)
        intent.putExtra("friendAccount", friend.friendAccount)
        intent.putExtra("username", friend.username)
        startActivity(intent)
        finish()
    }
}

class FriendAdapter(private val friends: List<FriendDetail>, private val clickListener: (FriendDetail) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val friendName: TextView = view.findViewById(R.id.friendName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends[position]
        holder.friendName.text = friend.username
        holder.itemView.setOnClickListener { clickListener(friend) }
    }

    override fun getItemCount() = friends.size
}