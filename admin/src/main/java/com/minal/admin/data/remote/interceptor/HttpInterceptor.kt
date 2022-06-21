package com.app.koyn.data.remote.interceptor

import android.content.Context
import android.os.Looper.loop
import android.util.Log
import com.minal.admin.AdminActivity
import com.minal.admin.R
import com.minal.admin.constant.PrefConstant
import com.minal.admin.constant.Preferences
import com.minal.admin.data.remote.RestApi
import com.minal.admin.ext_fun.addBearer
import com.minal.admin.ext_fun.showToast
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.Throws


internal class HttpInterceptor(var androidContext: Context) : Interceptor, KoinComponent {

    companion object{
        const val USER_AUTH_TOKEN: String = "access_token"
        const val AUTHORIZATION = "Authorization"

    }

    private val prefManager by inject<Preferences>()
    private val mRestApi by inject<RestApi>()


    @Throws(IOException::class,SocketTimeoutException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token: String? = prefManager.getString(USER_AUTH_TOKEN)
        val builder = request.newBuilder().addHeader(AUTHORIZATION, token.addBearer() ?: "")
        request = builder.build() //overwrite old request
        var response = chain.proceed(request) //perform request, here original request will be executed

        if (response.code == 401) { //if unauthorized
                synchronized(this) {
                        //perform all 401 in sync blocks, to avoid multiply token updates

                        val currentToken: String? = prefManager.getString(USER_AUTH_TOKEN)
                        if (currentToken != null && currentToken == token) {
                            //compare current token with token that was stored before,
                            // if it was not updated - do update
//                            val code = refreshToken() //refresh token
//                            if (!code) {
//                                sendBackToLogIn()
//                            }
                        }
                        if (prefManager.getString(USER_AUTH_TOKEN) != null) {
                            //retry requires new auth token,
                            Log.d("API_CALLED", "API Called with new token")
                            setAuthHeader(
                                    builder,
                                    prefManager.getString(USER_AUTH_TOKEN).addBearer()
                            ) //set auth token to updated
                            request = builder.build()
                            response?.close()
                            response = chain.proceed(request)
                            //repeat request with new token
                            //if again we get any error then send back to login
                            if (response.code != 200) {
                                sendBackToLogIn()
                            }

                    }
                }


        }
        //user has not uploaded profile pick navigate to profile scan
        else if(response.code == HttpURLConnection.HTTP_NOT_ACCEPTABLE){
            synchronized(this){
//                ScanFaceActivity.open(androidContext,AppConstant.TAG_ALREADY_USER,true)
                return@synchronized
            }
        }
        return response
    }

    private fun setAuthHeader(
            builder: Request.Builder,
            token: String?
    ) {
        if (token != null) //Add Auth token to each request if authorized
            builder.header(AUTHORIZATION, token)
    }

/*    private fun refreshToken(): Boolean {
        val semaphore = Semaphore(0)
        val succeeded = AtomicBoolean()
        //Refresh token, synchronously, save it, and return result code
        //you might use retrofit here
        AWSMobileClient.getInstance().getTokens(object : Callback<Tokens?> {
            override fun onResult(result: Tokens?) {
                if(result != null){
                    prefManager.save(PrefConstant.USER_AUTH_TOKEN, result.accessToken.tokenString)
                }else{
                    sendBackToLogIn()
                }
                succeeded.set(true);
                semaphore.release();
            }
            override fun onError(e: Exception) {
                Log.e("Err", e.message.toString())
                sendBackToLogIn()
                succeeded.set(true);
                semaphore.release();
            }


        })
        semaphore.acquire();
        return succeeded.get();
    }*/

    private fun sendBackToLogIn() {
//        if(KoynApplication.currentActivity != null && KoynApplication.currentActivity!!::class.java != AdminActivity::class.java){
//         prefManager.deleteAllPreference()
//            showToast()
//            AdminActivity.openActivity(context = androidContext, setFlag = true)
//        }
    }

    private fun showToast() {
       androidContext.showToast(androidContext.getString(R.string.un_authorized))
    }

}