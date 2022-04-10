package com.hemp.works.dashboard.notification.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.notification.data.repository.NotificationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel  @Inject constructor(private val repository: NotificationRepository)
    : BaseViewModel(repository)  {

        val notificationList = repository.notificationList.map { notification ->
            notification.filter { it.text != null && it.date != null }
        }

        private val _notificationVisibility= MutableLiveData(false)
        val notificationVisibility: LiveData<Boolean> = _notificationVisibility

        init {
            viewModelScope.launch {
                repository.fetchNotifications()
            }
        }

        fun handleNotificationVisibility(isEmpty: Boolean) {
            _notificationVisibility.postValue(!isEmpty)
         }
}