package com.hemp.works.dashboard.support.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Chat
import com.hemp.works.dashboard.model.RequestMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SupportService {

    @GET("/v1/chat/doctor/{id}")
    suspend fun initChat( @Path("id") id: String) : Response<Chat>

    @POST("/v1/chat/message")
    suspend fun sendMessage( @Body message: RequestMessage) : Response<Chat>


}