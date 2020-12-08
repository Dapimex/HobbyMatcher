package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.request.InvitationCreateRequest
import com.madness.hobbymatcher.networking.response.InvitationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InvitationService {
    @GET("invitations")
    fun getMyInvitations(
        @Query("id") id: Int,
        @Query("username") username: String
    ): Call<InvitationResponse>

    @POST("invitations/{id}/accept")
    fun acceptInvitation(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<ResponseBody>

    @POST("invitations/{id}/decline")
    fun declineInvitation(
        @Path("id") id: Int,
        @Query("username") username: String
    ): Call<ResponseBody>

    @POST("invitations/create")
    fun createInvitation(
        @Query("createRequest") createRequest: InvitationCreateRequest,
        @Query("id") id: Int,
        @Query("username") username: String
    )
}