package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.SignIn
import com.madness.hobbymatcher.networking.response.SignUp
import com.madness.hobbymatcher.networking.response.WhoAmI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("signIn")
    fun signIn(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<SignIn>

    @POST("signUp")
    fun signUp(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<SignUp>

    @GET("whoami")
    fun whoAmI(
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<WhoAmI>
}