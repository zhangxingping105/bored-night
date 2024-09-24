package com.zxp.boredom.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zxp.boredom.ApiResponse
import com.zxp.boredom.AuthInterceptor
import com.zxp.boredom.ChatActivity
import com.zxp.boredom.FriendDetail
import com.zxp.boredom.FriendListActivity
import com.zxp.boredom.FriendService
import com.zxp.boredom.GlobalVariableManager
import com.zxp.boredom.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
*
*
* @description:
* @author: zxp
* @date: 2024/9/23 22:45
*/
class FriendsFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendAdapter: FriendAdapter // 需要实现RecyclerView.Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_friend_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        var account = GlobalVariableManager.getAccount()
        queryFriend(account.toString())
        return view
    }

    private fun queryFriend(account: String) {
        val baseUrl = this.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://${baseUrl}")
            .client(
                OkHttpClient.Builder()
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
                        val intent = Intent(requireActivity(), ChatActivity::class.java).apply {
                            putExtra("account", account)
                            putExtra("friendAccount", friend.friendAccount)
                            putExtra("username", friend.username)
                        }
                        startActivity(intent)
                    }
                    recyclerView.adapter = friendAdapter
                } else {
                    Toast.makeText(requireContext(), "获取好友列表失败", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<FriendDetail>>>, t: Throwable) {
                Toast.makeText(requireContext(), "获取好友列表失败", Toast.LENGTH_SHORT).show()
            }
        })
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