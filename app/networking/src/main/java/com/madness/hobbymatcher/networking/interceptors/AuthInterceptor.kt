package com.example.android.lasttorture.data.interceptors

import com.madness.hobbymatcher.networking.interceptors.CredentialsStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor
@Inject constructor(
    private val credentialsStore: CredentialsStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", credentialsStore.token)

        return chain.proceed(requestBuilder.build())
    }
}