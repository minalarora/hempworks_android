package com.hemp.works.dashboard.notification.data.remote

import com.hemp.works.dashboard.model.CreditHistory
import com.hemp.works.dashboard.model.Notification
import com.hemp.works.dashboard.model.Payment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotificationService {

    @GET("/v1/notification/me")
    suspend fun getNotifications() : Response<List<Notification>>


}