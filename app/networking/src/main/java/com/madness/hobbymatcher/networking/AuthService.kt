package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.SignInResponse
import com.madness.hobbymatcher.networking.response.SignUpResponse
import com.madness.hobbymatcher.networking.response.WhoAmIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("signIn")
    fun signIn(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<SignInResponse>

    @POST("signUp")
    fun signUp(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<SignUpResponse>

    @GET("whoami")
    fun whoAmI(
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<WhoAmIResponse>
}