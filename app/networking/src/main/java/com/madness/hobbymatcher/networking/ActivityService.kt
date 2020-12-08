package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.request.ActivityCreateRequest
import com.madness.hobbymatcher.networking.response.ActivityResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ActivityService {
    @GET("activities")
    fun getVisibleActivities(
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<Array<ActivityResponse>>

    @POST("activities")
    fun createActivity(
        @Query("createRequest") createRequest: ActivityCreateRequest,
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<ActivityResponse>

    @GET("activities/{id{")
    fun getActivity(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<ActivityResponse>

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
    fun getMyActivities(): Call<Array<ActivityResponse>>

    @GET("activities/joined")
    fun getJoinedActivities(): Call<Array<ActivityResponse>>
}
