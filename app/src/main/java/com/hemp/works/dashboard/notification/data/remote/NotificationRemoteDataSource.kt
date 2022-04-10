package com.hemp.works.dashboard.notification.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class NotificationRemoteDataSource @Inject constructor(private val service: NotificationService): BaseDataSource() {

    suspend fun getNotification() = getResult { service.getNotifications() }
}