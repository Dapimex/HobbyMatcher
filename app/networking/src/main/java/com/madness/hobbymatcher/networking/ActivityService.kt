package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.Activity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ActivityService {
    @GET("activities")
    fun getVisibleActivities(
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<Array<Activity>>

    @POST("activities")
    fun createActivity(
        @Query("createRequest") createRequest: Activity,
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<Activity>

    @GET("activities/{id{")
    fun getActivity(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<Activity>

    @DELETE("activities/{id}")
    fun deleteActivity(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<ResponseBody>

    @POST("activities/{id}/join")
    fun addUserToActivity(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<ResponseBody>

    @GET("activities/created")
    fun getMyActivities(): Call<Array<Activity>>

    @GET("activities/joined")
    fun getJoinedActivities(): Call<Array<Activity>>
}
