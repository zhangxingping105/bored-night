package com.zxp.boredom

/**
 * 全局变量管理器
 *
 * @description:
 * @author: zxp
 * @date: 2024/9/21 9:46
 */
object GlobalVariableManager {
    private var token: String? = null

    private var account: String? = null

    fun setToken(newToken: String?) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    fun getToken(): String? {
        return token
    }

    fun setAccount(loginAccount: String?) {
        account = loginAccount
    }

    fun getAccount(): String? {
        return account
    }
}