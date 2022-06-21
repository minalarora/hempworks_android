package com.minal.admin.di_coin



import com.hoobio.base.BaseViewModel
import com.minal.admin.data.viewmodel.AdminViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/**
 * Created at 30/03/2020.
 */

val viewModelModule = module {
  viewModel { BaseViewModel() }
  viewModel { AdminViewModel() }


}