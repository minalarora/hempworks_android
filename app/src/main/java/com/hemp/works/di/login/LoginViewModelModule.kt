package com.hemp.works.di.login

import androidx.lifecycle.ViewModel
import com.hemp.works.di.ViewModelKey
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class LoginViewModelModule {

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
}