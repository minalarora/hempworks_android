package com.hemp.works.di.dashboard

import com.hemp.works.dashboard.DashboardActivity
import com.hemp.works.di.login.LoginFragmentBuildersModule
import com.hemp.works.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DashboardActivityModule {

    @ContributesAndroidInjector(modules = [DashboardFragmentBuildersModule::class])
    abstract fun contributeDashboardActivity(): DashboardActivity



}