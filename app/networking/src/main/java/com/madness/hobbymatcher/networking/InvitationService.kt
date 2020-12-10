package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.Invitation
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface InvitationService {
    @GET("invitations")
    fun getMyInvitations(): Call<List<Invitation>>

    @POST("invitations/{id}/accept")
    fun acceptInvitation(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @POST("invitations/{id}/decline")
    fun declineInvitation(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @POST("invitations/create")
    fun createInvitation(
        @Body invitation: Invitation
    )
}