package com.zxp.boredom

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 权限拦截器
 *
 * @description: 权限拦截器
 * @author: zxp
 * @date: 2024/9/21 9:38
 */
class AuthInterceptor(private val account: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()


        TokenManager.getToken()?.let { requestBuilder.addHeader("Authorization", it) }

        requestBuilder.addHeader("account", account)
        return chain.proceed(requestBuilder.build())
    }
}