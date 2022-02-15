package com.hemp.works.dashboard.profile.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.profile.data.repository.ProfileRepository
import javax.inject.Inject

class ProfileViewModel  @Inject constructor(private val repository: ProfileRepository): BaseViewModel() {
}