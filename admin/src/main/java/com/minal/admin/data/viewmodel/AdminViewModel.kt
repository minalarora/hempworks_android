package com.minal.admin.data.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minal.admin.data.remote.RestApi
import com.minal.admin.data.remote.Result
import com.minal.admin.data.repo.AdminRepository
import com.minal.admin.data.request.*
import com.minal.admin.data.response.*
import com.minal.admin.data.response.ledger.Ledger
import com.minal.admin.data.response.ledger.ResponsePendingAmount
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    val allOrderList: MutableLiveData<Result<ArrayList<OrderList>>> by lazy {
        MutableLiveData<Result<ArrayList<OrderList>>>()
    }

    val walletHistory: MutableLiveData<Result<List<ResponseWalletHistory>>> by lazy {
        MutableLiveData<Result<List<ResponseWalletHistory>>>()
    }
    val transactionAll: MutableLiveData<Result<ArrayList<ResponseTransactionAll>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponseTransactionAll>>>()
    }
    val creditHistory: MutableLiveData<Result<ArrayList<ResponseCreditHistory>>> by lazy {
        MutableLiveData<Result<ArrayList<ResponseCreditHistory>>>()
    }
    val creditHistoryPending: MutableLiveData<Result<ResponseCreditHistoryPending>> by lazy {
        MutableLiveData<Result<ResponseCreditHistoryPending>>()
    }

    val updateOrder: MutableLiveData<Result<ResponseOrderUpdate>> by lazy {
        MutableLiveData<Result<ResponseOrderUpdate>>()
    }

    val createBanner: MutableLiveData<Result<ResponseCreateBanner>> by lazy {
        MutableLiveData<Result<ResponseCreateBanner>>()
    }

    val createBlog: MutableLiveData<Result<ResponseBlog>> by lazy {
        MutableLiveData<Result<ResponseBlog>>()
    }

    val liveSession: MutableLiveData<Result<ResponseLiveSession>> by lazy {
        MutableLiveData<Result<ResponseLiveSession>>()
    }

    val newsLetter: MutableLiveData<Result<ResponseNewsLetter>> by lazy {
        MutableLiveData<Result<ResponseNewsLetter>>()
    }

    val tutorialData: MutableLiveData<Result<ResponseTutorial>> by lazy {
        MutableLiveData<Result<ResponseTutorial>>()
    }

    val uploadImage: MutableLiveData<Result<ResponseUploadImage>> by lazy {
        MutableLiveData<Result<ResponseUploadImage>>()
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


    fun allOrder(token: String)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().getAllOrder(token)
            allOrderList.postValue(result)
            loading.postValue(false)

        }
    }



    fun updateOrder(token: String,id:String?,mRequestOrderUpdate: RequestOrderUpdate)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().updateOrder(token,id,mRequestOrderUpdate)
            updateOrder.postValue(result)
            loading.postValue(false)

        }
    }


    private val _ledgerList = MutableLiveData<List<Ledger>>()
    val ledgerList: LiveData<List<Ledger>>
        get() = _ledgerList

    private val _paymentResponse = MutableLiveData<ResponsePendingAmount>()
    val paymentResponse: LiveData<ResponsePendingAmount>
        get() = _paymentResponse

    fun ledger(token: String, doctor: String){
        viewModelScope.launch {
            loading.postValue(true)


            val adminRepository = AdminRepository()
            val deferredOrderList = async { adminRepository.getAllOrderLedger(token, doctor) }
            val deferredWalletList = async { adminRepository.walletHistory(token, doctor) }
            val deferredCreditList = async { adminRepository.creditHistory(token, doctor)}
            val deferredPaymentList = async { adminRepository.transactionAll(token, doctor) }
            val deferredPaymentResponse = async { adminRepository.creditHistoryPending(token, doctor) }

            val localLedgerList: ArrayList<Ledger> = ArrayList();

            deferredOrderList.await().let {
                if (it is Result.Success) localLedgerList.addAll(it.data.map { it -> Ledger(it.date, it) })
            }

            deferredWalletList.await().let {
                if (it is Result.Success) localLedgerList.addAll(it.data.map { it -> Ledger(it.date, it) })
            }
            deferredCreditList.await().let {
                if (it is Result.Success) localLedgerList.addAll(it.data.map { it -> Ledger(it.date, it) })
            }
            deferredPaymentList.await().let {
                if (it is Result.Success) localLedgerList.addAll(it.data.map { it -> Ledger(it.date, it) })
            }

            deferredPaymentResponse.await().let {
                if (it is Result.Success) _paymentResponse.postValue(it.data)
            }

            localLedgerList.sortByDescending { it.date }
            _ledgerList.postValue(localLedgerList)

            loading.postValue(false)

        }
    }

    fun createBanner(token: String?,mRequestCreateBanner: RequestCreateBanner)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().createBanner(token,mRequestCreateBanner)
            createBanner.postValue(result)
            loading.postValue(false)

        }
    }

    fun createBlog(token: String?,mRequestBlog: RequestBlog)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().createBlog(token,mRequestBlog)
            createBlog.postValue(result)
            loading.postValue(false)

        }
    }

    fun liveSession(token: String?,mRequestLiveSession: RequestLiveSession)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().liveSession(token,mRequestLiveSession)
            liveSession.postValue(result)
            loading.postValue(false)

        }
    }

    fun newsLetter(token: String?,mRequestNewsLetter: RequestNewsLetter)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().newsLetter(token,mRequestNewsLetter)
            newsLetter.postValue(result)
            loading.postValue(false)

        }
    }

    fun tutorial(token: String?,mRequestTutorial: RequestTutorial)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().tutorial(token,mRequestTutorial)
            tutorialData.postValue(result)
            loading.postValue(false)

        }
    }

    fun uploadImage(token: String,mFile: File)
    {
        viewModelScope.launch {
            loading.postValue(true)
            val result = AdminRepository().uploadImage(token,mFile)
            uploadImage.postValue(result)
            loading.postValue(false)

        }
    }























    private val _ordersVisibility= MutableLiveData(false)
    val ordersVisibility: LiveData<Boolean> = _ordersVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<OrderList>): List<OrderList> {
        val filteredList = if (dateRange != null) {
            list.filter { order ->
                order.date!!.after(dateRange!!.first) && order.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }
        val expandedList = mutableListOf<OrderList>()
        for (orderObject in filteredList) {
            if (orderObject.order.isNullOrEmpty()) continue
            for (orderProduct in orderObject.order) {
                val order = orderObject.copy(order = listOf(orderProduct))
                expandedList.add(order)
            }
        }
        return expandedList

    }

    fun updateDateRange(startDateInMillis: Long, endDateInMillis: Long) {
        if (startDateInMillis < 1 || endDateInMillis < 1) {
            dateRange = null
            _dateRangeVisibility.postValue(false)
        }
        else {
            dateRange = Pair(
                Calendar.getInstance().apply {
                    timeInMillis = startDateInMillis
                    add(Calendar.HOUR, -5)
                }.time,
                Calendar.getInstance().apply {
                    timeInMillis = endDateInMillis
                    add(Calendar.HOUR, 18)
                }.time
            )
            _dateRangeText.postValue("Showing results for " + dateFormat.format(dateRange!!.first) + " - " + dateFormat.format(dateRange!!.second))
            _dateRangeVisibility.postValue(true)
        }
//        orderList.postValue(filterList(orderList.value))
    }


}