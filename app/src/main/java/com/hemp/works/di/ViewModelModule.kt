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
import com.hemp.works.dashboard.product.ui.ProductViewModel
import com.hemp.works.dashboard.profile.ui.ProfileViewModel
import com.hemp.works.dashboard.search.ui.SearchViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.*

@Suppress("unused")
@Module
abstract class ViewModelModule {

    //LOGIN
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    abstract fun bindForgetPasswordViewModel(viewModel: ForgetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateViewModel::class)
    abstract fun bindCreateViewModel(viewModel: CreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerifyMobileViewModel::class)
    abstract fun bindVerifyMobileViewModel(viewModel: VerifyMobileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginSharedViewModel::class)
    abstract fun bindLoginSharedViewModel(viewModel: LoginSharedViewModel): ViewModel

    //DASHBOARD
    @Binds
    @IntoMap
    @ViewModelKey(DashboardSharedViewModel::class)
    abstract fun bindDashboardSharedViewModel(viewModel: DashboardSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrescriptionViewModel::class)
    abstract fun bindPrescriptionViewModel(viewModel: PrescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DosageCalculatorViewModel::class)
    abstract fun bindDosageCalculatorViewModel(viewModel: DosageCalculatorViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}