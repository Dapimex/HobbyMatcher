package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.request.SignInRequest
import com.madness.hobbymatcher.networking.request.SignUpRequest
import com.madness.hobbymatcher.networking.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("signIn")
    fun signIn(
        @Body singIn: SignInRequest
    ): Call<AuthResponse>

    @POST("signUp")
    fun signUp(
        @Body signUp: SignUpRequest
    ): Call<AuthResponse>
}