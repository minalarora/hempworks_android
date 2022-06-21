package com.minal.admin.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minal.admin.data.remote.RestApi
import com.minal.admin.data.remote.Result
import com.minal.admin.data.repo.AdminRepository
import com.minal.admin.data.request.*
import com.minal.admin.data.response.*
import kotlinx.coroutines.launch

class AdminViewModel() : ViewModel() {

    val homeCategory: MutableLiveData<Result<ArrayList<ResponseProduct>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponseProduct>>>()
    }

    val coupons: MutableLiveData<Result<ResponseCreateCoupon>> by lazy {
        MutableLiveData<Result<ResponseCreateCoupon>>()
    }

    val allDoctors: MutableLiveData<Result<ArrayList<ResponseAllDoctors>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponseAllDoctors>>>()
    }

    val doctorDetail: MutableLiveData<Result<ResponseDoctorDetail>> by lazy {
        MutableLiveData<Result<ResponseDoctorDetail>>()
    }

    val orderList: MutableLiveData<Result<ArrayList<OrderList>>> by lazy {
        MutableLiveData<Result<ArrayList<OrderList>>>()
    }

    val singleOrder: MutableLiveData<Result<ResponseSingleOrder>> by lazy {
        MutableLiveData<Result<ResponseSingleOrder>>()
    }

    val presList: MutableLiveData<Result<ArrayList<ResponsePresList>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponsePresList>>>()
    }

    val docUpStatus: MutableLiveData<Result<ResponseDoctorStatus>> by lazy {
        MutableLiveData<Result<ResponseDoctorStatus>>()
    }

    val creditManual: MutableLiveData<Result<RespCreditManual>> by lazy {
        MutableLiveData<Result<RespCreditManual>>()
    }

    val manualOrder: MutableLiveData<Result<RespManualOrder>> by lazy {
        MutableLiveData<Result<RespManualOrder>>()
    }

    val chatList: MutableLiveData<Result<ArrayList<ResponseChatList>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponseChatList>>>()
    }

    val singleChats: MutableLiveData<Result<RespSingleChatList>> by lazy {
        MutableLiveData<Result<RespSingleChatList>>()
    }

    val sendMSG: MutableLiveData<Result<ResponseSendMsg>> by lazy {
        MutableLiveData<Result<ResponseSendMsg>>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val noInternet: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getProductAll(token: String)
    {

        viewModelScope.launch {
            loading.postValue(true)
            val result:Result<ArrayList<ResponseProduct>> = AdminRepository().getProduct(token)
            homeCategory.postValue(result)
//            when(result)
//            {
//                is Result.Success ->
//                {
//                    homeCategory.postValue(result.data)
//                }
//                is Result.Error ->
//                {
//                    errorMessage.postValue(result.exception)
////                    noInternet.postValue(result.noInternet)
//                }
            //}
            loading.postValue(false)

        }
    }


    fun createCoupon(token: String,obj:RequestCreateCoupon)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().createCoupon(token,obj)
            coupons.postValue(result)
            loading.postValue(false)

        }
    }

    fun getDoctors(token: String,obj:RequestAllDoctors)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getDoctors(token,obj)
            allDoctors.postValue(result)
            loading.postValue(false)

        }
    }


    fun getDoctorDetail(token: String,obj:RequestSingleDoctor)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getDoctorDetail(token,obj)
            doctorDetail.postValue(result)
            loading.postValue(false)

        }
    }


    fun getOrderList(token: String,obj:RequestOrderList)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getOrderList(token,obj)
            orderList.postValue(result)
            loading.postValue(false)

        }
    }

    fun getSingleOrder(token: String,obj:RequestSingleOrder)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getSingleOrder(token,obj)
            singleOrder.postValue(result)
            loading.postValue(false)

        }
    }

    fun getPresList(token: String,obj:RequestPresList)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getPresList(token,obj)
            presList.postValue(result)
            loading.postValue(false)

        }
    }

    fun getUpdateStatus(token: String,id:String?,obj:RequestDocUpStatus)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getUpdateStatus(token,id,obj)
            docUpStatus.postValue(result)
            loading.postValue(false)

        }
    }

    fun addCreditManual(token: String,obj:ReqAddCreditManual)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().addCreditManual(token,obj)
            creditManual.postValue(result)
            loading.postValue(false)

        }
    }

    fun orderManual(token: String,obj:ReqAddManualOrder)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().orderManual(token,obj)
            manualOrder.postValue(result)
            loading.postValue(false)

        }
    }


    fun chatLists(token: String)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().chatList(token)
            chatList.postValue(result)
            loading.postValue(false)

        }
    }

    fun singleChat(token: String,id:String)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().singleChat(token,id)
            singleChats.postValue(result)
            loading.postValue(false)

        }
    }

    fun sendMsgs(token: String,obj:RequestSendMsg)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().sendMsg(token,obj)
            sendMSG.postValue(result)
            loading.postValue(false)

        }
    }



}