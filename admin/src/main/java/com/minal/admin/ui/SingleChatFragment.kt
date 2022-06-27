package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoobio.base.BaseFragment
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestCreateCoupon
import com.minal.admin.data.request.RequestSendMsg
import com.minal.admin.data.response.Message
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAdminBinding
import com.minal.admin.databinding.FragmentSingleChatBinding
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class SingleChatFragment: BaseFragment<FragmentSingleChatBinding>(), ChatItemClickListener, SupportItemClickListener{

    private lateinit var viewModel : AdminViewModel
    private val mRequestSendMsg by lazy { RequestSendMsg() }

    var token:String?=null
    var ids:String?=null

    companion object {
        val TAG: String = SingleChatFragment::class.java.simpleName
        fun getInstance(id:String) = SingleChatFragment().also {
            it.arguments = bundleOf(BundleConstant.ID to id)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentSingleChatBinding {
        return FragmentSingleChatBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        ids = arguments?.getString(BundleConstant.ID)

        token?.let { it1 -> ids?.let { viewModel.singleChat(it1, it) } }





        viewModel?.singleChats.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {


                    Log.d("data", it.data.toString())

                    mBinding.idRvChat.adapter = ChatAdapter(this)

                    (mBinding.idRvChat.adapter as ChatAdapter).submitList(it.data.messages)
                    if((mBinding.idRvChat.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() > (mBinding.idRvChat.adapter as ChatAdapter).itemCount - 2) {
                        mBinding.idRvChat.scrollToPosition((mBinding.idRvChat.adapter as ChatAdapter).itemCount - 1)
                    }

                    Timer().schedule(3000) {
                        token?.let { it1 -> ids?.let { viewModel.singleChat(it1, it) } }
                    }
                }

                is Result.Error -> {

                }
            }
        }

        viewModel?.sendMSG?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {

                    Log.d("datamsg", it.data.toString())
                }

                is Result.Error -> {

                }
            }
        }

    }

    private fun setListener() {

        mBinding.idTvSend.setOnClickListener {
            mRequestSendMsg.apply {
                id = ids
                message = mBinding.idEdtName.text.toString()
                type = "TEXT"
                isDoctor = false
            }
            token?.let { it1 -> viewModel.sendMsgs(it1,mRequestSendMsg) }

            mBinding.idEdtName.setText("")
        }
    }

    override fun onRetry(message: Message) {
        TODO("Not yet implemented")
    }



}

interface ChatItemClickListener{
    fun onRetry(message: Message)
}

interface SupportItemClickListener{
    fun onRetry(message: Message)
}

