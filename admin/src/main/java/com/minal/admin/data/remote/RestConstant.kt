package com.minal.admin.data.remote

import com.minal.admin.BuildConfig

object RestConstant {

    const val AUTH_TOKEN = "auth_token"
    const val API_TIME_OUT = 50


    var BASE_URL=  if (BuildConfig.DEBUG) "https://hempworks-web-l5hj7.ondigitalocean.app"
                        else "https://api.techhempworks.co.in"

    const val API_PRODUCT_ALL="/v1/product/all/admin"
    const val API_CREATE_COUPON = "/v1/coupon"
    const val API_DOCTOR_LIST= "/v1/doctor/list"
    const val API_SINGLE_DOCTOR ="/v1/doctor/single"
    const val API_ORDER_LIST ="/v1/order/list"
    const val API_ORDER_SINGLE ="/v1/order/single"
    const val API_PRESCRIPTION_LIST ="/v1/prescription/list"
    const val API_DOCTOR_UPDATE_STATUS ="/v1/doctor/update/status"
    const val API_ADD_MANUAL_CREDIT ="/v1/credithistory/manual"
    const val API_ORDER_MANUAL = "/v1/order/manual"
    const val API_CHAT_LIST="/v1/chat/list"
    const val API_CHAT_ADMIN ="/v1/chat/admin/{id}"
    const val API_CHAT_MESSAGE ="/v1/chat/message"
    const val API_ALL_ORDER="/v1/order/all/admin"
    const val API_ORDER_UPDATE="/v1/order/update"
    const val API_WALLET_HISTORY="/v1/wallethistory/admin"
    const val API_TRANSACTION_ALL="/v1/transaction/all/admin"
    const val API_CREDIT_HISTORY="/v1/credithistory/admin"
    const val API_CREDIT_HISTORY_PENDIG="/v1/credithistory/pending/admin"
    const val API_CREATE_BANNER ="/v1/banner"
    const val API_BLOG="/v1/blog"
    const val API_LIVE_SESSION="/v1/livesession"
    const val API_NEWS_PAPER="/v1/newspaper"
    const val API_TUTORIAL = "/v1/tutorial"
    const val API_PRODUCT_UPDATE="/v1/product/update"
    const val API_VARIANT_UPDATE="/v1/variant/update"
    const val API_UPLOAD_IMAGE="/v1/upload/image"




}