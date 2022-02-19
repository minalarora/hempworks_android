package com.hemp.works.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorViewModel
import com.hemp.works.dashboard.home.ui.HomeViewModel
import com.hemp.works.dashboard.home.ui.ProductListViewModel
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.dashboard.prescription.ui.UploadPrescriptionViewModel
import com.hemp.works.dashboard.product.ui.ProductViewModel
import com.hemp.works.dashboard.profile.ui.ProfileViewModel
import com.hemp.works.dashboard.search.ui.SearchViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.*

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}