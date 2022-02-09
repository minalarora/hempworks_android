package com.hemp.works

import android.app.Application
import android.app.Activity
import com.facebook.stetho.Stetho
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideCustomImageLoader
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class HempWorksApplication: Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AppInjector.init(this)

        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        BigImageViewer.initialize(GlideCustomImageLoader.with(applicationContext, MyAppGlideModule.okHttpClient))
    }

    override fun activityInjector() = dispatchingAndroidInjector
}