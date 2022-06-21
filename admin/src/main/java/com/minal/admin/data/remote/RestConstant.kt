package com.minal.admin.data.remote

object RestConstant {

       const val AUTH_TOKEN = "auth_token"

    const val API_TIME_OUT = 50

    const val BASE_URL=  "https://hempworks-web-l5hj7.ondigitalocean.app"
    const val API_PRODUCT_ALL="/v1/product/all"
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



}