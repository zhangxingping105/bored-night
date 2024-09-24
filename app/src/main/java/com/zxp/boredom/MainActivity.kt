package com.zxp.boredom

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zxp.boredom.fragment.FriendsFragment
import com.zxp.boredom.fragment.MessagesFragment
import com.zxp.boredom.fragment.ProfileFragment
import com.zxp.boredom.ui.theme.BoredomTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // 设置默认Fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MessagesFragment()).commit()

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_messages -> MessagesFragment()
                R.id.nav_friends -> FriendsFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it).commit()
            }
            true
        }
    }
}