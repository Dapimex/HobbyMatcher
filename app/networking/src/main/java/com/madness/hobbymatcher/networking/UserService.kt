package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.User
import com.madness.hobbymatcher.networking.response.UsersResponse
import com.madness.hobbymatcher.networking.response.WhoAmI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    fun getAllUsersWithUsernameLike(@Query("pattern") pattern: String): Call<UsersResponse>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @GET("whoami")
    fun whoAmI(): Call<WhoAmI>

    @GET("users/username")
    fun getUserByUsername(@Query("username") username: String): Call<User>
}