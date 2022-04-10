package com.hemp.works.dashboard.notification.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.credit.data.remote.CreditRemoteDataSource
import com.hemp.works.dashboard.model.CreditHistory
import com.hemp.works.dashboard.model.Notification
import com.hemp.works.dashboard.model.Payment
import com.hemp.works.dashboard.notification.data.remote.NotificationRemoteDataSource
import com.hemp.works.login.data.model.Address
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(private val remoteDataSource: NotificationRemoteDataSource) : BaseRepository() {

    private val _notificationList = MutableLiveData<List<Notification>>()
    val notificationList: LiveData<List<Notification>>
        get() = _notificationList


    suspend fun fetchNotifications() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getNotification()
        }?.let{
            it.data?.let { list -> _notificationList.postValue(list) }
        }
    }

}