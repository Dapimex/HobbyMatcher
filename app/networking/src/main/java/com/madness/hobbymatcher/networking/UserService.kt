package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    fun getAllUsersWithUsernameLike(@Query("pattern") pattern: String): Call<Array<User>>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>
}