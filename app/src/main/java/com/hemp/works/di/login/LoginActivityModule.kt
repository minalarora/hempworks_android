package com.hemp.works.di.login

import com.hemp.works.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module(includes = [LoginDataModule::class, LoginViewModelModule::class])
abstract class LoginActivityModule {

    @ContributesAndroidInjector(modules = [LoginFragmentBuildersModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

}