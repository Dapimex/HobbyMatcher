package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.Invitation
import com.madness.hobbymatcher.networking.response.Invitations
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InvitationService {
    @GET("invitations")
    fun getMyInvitations(): Call<Invitations>

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
    ): Call<ResponseBody>
}