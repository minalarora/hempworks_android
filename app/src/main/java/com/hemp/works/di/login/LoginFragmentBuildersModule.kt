package com.hemp.works.di.login

import com.hemp.works.login.ui.*;
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class LoginFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateFragment(): CreateFragment

    @ContributesAndroidInjector
    abstract fun contributeForgetPasswordFragment(): ForgetPasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeOnboardingFragment(): OnboardingFragment

    @ContributesAndroidInjector
    abstract fun contributeVerifyMobileFragment(): VerifyMobileFragment


}