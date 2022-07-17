package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestOrderList
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAllOrderBinding
import com.minal.admin.ext_fun.addFragment
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.utils.OrderListener

class AllOrderAdminFragment: BaseFragment<FragmentAllOrderBinding>(), OrderListener {

    private lateinit var viewModel : AdminViewModel
    private val mRequestOrderList by lazy { RequestOrderList() }
    var token:String?=null
    var docType:String?=null
    var orderId:String?=null

    private val mAllOrderAdminAdapter by lazy {
        AllOrderAdminAdapter(requireContext(),this)
    }

    companion object {
        val TAG: String = AllOrderAdminFragment::class.java.simpleName
        fun getInstance() = AllOrderAdminFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAllOrderBinding {
        return FragmentAllOrderBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()


        token?.let {
            viewModel?.allOrder(it)
        }

        viewModel?.allOrderList.observe(viewLifecycleOwner) {
            Log.d("op","op")
            Log.d("op",it.toString())


            when (it) {

                is Result.Success -> {
                    Log.d("data",it.data.toString())
                    Log.d("op1","op")


                    mAllOrderAdminAdapter.addItems(it.data)


                    orderId = it.data.getOrNull(0)?.id.toString()

                    mBinding.idRvOrders.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = mAllOrderAdminAdapter
                    }

                }
            }
        }

    }

    private fun setListener() {

    }

    override fun dataPass(position: Int?, v: View?, data: OrderList?) {

        when(v?.id){

            R.id.idRlOrder->{
                replaceFragment(isAddToBackStack = true,
                    R.id.idFcvAdmin,
                    SingleOrderAdminFragment.getInstance(orderId),
                    SingleOrderAdminFragment.TAG
                )
            }
        }
    }

}