package com.hemp.works.di

import android.app.Application
import android.content.Context
import com.hemp.works.HempWorksApplication
import com.hemp.works.di.dashboard.DashboardActivityModule
import com.hemp.works.di.login.LoginActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        NetworkModule::class,
        AppModule::class,
        LoginActivityModule::class,
        DashboardActivityModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: HempWorksApplication)
}