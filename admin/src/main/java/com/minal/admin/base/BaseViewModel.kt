package com.hoobio.base

import androidx.lifecycle.ViewModel
import com.minal.admin.base.BaseRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created at 30/03/2020.
 */
open class BaseViewModel : ViewModel(), KoinComponent {

    private val baseRepository by inject<BaseRepository>()




}