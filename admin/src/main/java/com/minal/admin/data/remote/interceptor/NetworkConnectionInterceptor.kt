package com.app.koyn.data.remote.interceptor

import android.content.Context
import com.minal.admin.R
import com.minal.admin.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws


class NetworkConnectionInterceptor constructor(var context: Context) : Interceptor {
    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isOnline(context,false)) {
            throw NoInternetException(message = context.getString(R.string.no_internet_connection))
        }
        return chain.proceed(chain.request())
    }

}

class NoInternetException(message: String) : IOException(message)

