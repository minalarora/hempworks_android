package com.hemp.works.dashboard

import android.content.Context
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject

class DashboardSharedViewModel @Inject constructor(context: Context): BaseViewModel() {

    var user =  PreferenceManagerUtil.getDoctor(context)

    val userType: UserType
        get() {
        return when (user) {
            null -> UserType.ANONYMOUS
            else -> when (user?.status) {
                Constants.PENDING -> UserType.PENDING
                Constants.APPROVED -> UserType.APPROVED
                else -> UserType.ANONYMOUS
            }
        }
    }
}

