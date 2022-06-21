package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestAllDoctors
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseChatList
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentChatListBinding
import com.minal.admin.databinding.FragmentSingleChatBinding
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.utils.ChatListener
import com.minal.admin.utils.OrderListener

class ChatListFragment: BaseFragment<FragmentChatListBinding>(),ChatListener {

    private lateinit var viewModel : AdminViewModel
    var token:String?=null

    private val mChatListAdapter by lazy {
        ChatListAdapter(requireContext(),this)
    }

    companion object {
        val TAG: String = ChatListFragment::class.java.simpleName
        fun getInstance() = ChatListFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentChatListBinding {
        return FragmentChatListBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        token?.let { it1 -> viewModel.chatLists(it1) }

        viewModel?.chatList?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {

                    Log.d("data",it.data.toString())
                    mChatListAdapter.addItems(it.data)

                    mBinding.chatList.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = mChatListAdapter
                    }

                }

                is Result.Error -> {


                }
            }
        }
    }

    private fun setListener() {

    }


    override fun dataPass(position: Int?, v: View?, data: ResponseChatList?) {

        when(v?.id){
            R.id.idRlSingle->{
                data?.id?.let { SingleChatFragment.getInstance(it) }?.let {
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        it,
                        SingleChatFragment.TAG)
                }
            }
        }
    }


}