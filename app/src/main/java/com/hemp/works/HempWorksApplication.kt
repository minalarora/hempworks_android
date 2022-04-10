package com.hemp.works

import android.app.Application
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.facebook.stetho.Stetho
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideCustomImageLoader
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.di.AppInjector
import com.onesignal.OneSignal
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

        OneSignal.initWithContext(applicationContext);
        OneSignal.setAppId(BuildConfig.ONESIGNAL_APP_ID);
        OneSignal.setNotificationOpenedHandler(NotificationOpenedHandler(applicationContext))
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        if (BuildConfig.DEBUG) OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        createNotificationChannel()

        BigImageViewer.initialize(GlideCustomImageLoader.with(applicationContext, MyAppGlideModule.okHttpClient))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                "ANANTA PARTNERS",
                "ANANTA PARTNERS",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun activityInjector() = dispatchingAndroidInjector
}