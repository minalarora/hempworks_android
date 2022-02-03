package com.hemp.works.dashboard

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import com.hemp.works.utils.PreferenceManagerUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardSharedViewModel @Inject constructor(context: Context): BaseViewModel() {

    val user = PreferenceManagerUtil.getDoctor(context)

    val userType: UserType = when(user) {
        null -> UserType.ANONYMOUS
        else -> when (user.status) {
            Constants.PENDING -> UserType.PENDING
            else -> UserType.APPROVED
        }
    }
}

