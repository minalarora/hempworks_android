package com.hemp.works

import android.content.Context
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import org.json.JSONException

class NotificationOpenedHandler(val applicationContext: Context) : OneSignal.OSNotificationOpenedHandler {


    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenedResult) {

        try {


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}