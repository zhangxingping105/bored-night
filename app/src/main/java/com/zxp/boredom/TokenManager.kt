package com.zxp.boredom

/**
 * token管理
 *
 * @description:
 * @author: zxp
 * @date: 2024/9/21 9:46
 */
object TokenManager {
    private var token: String? = null

    fun setToken(newToken: String?) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    fun getToken(): String? {
        return token
    }
}