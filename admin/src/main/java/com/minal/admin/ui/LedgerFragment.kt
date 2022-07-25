package com.minal.admin.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestBlog
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ledger.Ledger
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateBlogBinding
import com.minal.admin.databinding.FragmentLedgerBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils
import com.minal.admin.utils.OrderListener
import java.io.File
import java.text.SimpleDateFormat

class LedgerFragment : BaseFragment<FragmentLedgerBinding>() {

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val mRequestBlog by lazy { RequestBlog() }


    private lateinit var viewModel : AdminViewModel
    var docId:String?=null
    var token:String?=null
    var imageFile: File?=null


    private  val PICK_FILE_REQUEST = 100
    private  val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 101

    companion object {
        val TAG: String = LedgerFragment::class.java.simpleName
        fun getInstance(docId: String?) = LedgerFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to docId)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentLedgerBinding {
        return FragmentLedgerBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi(){

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()
        docId = arguments?.getString(BundleConstant.DOCTOR_ID)

        token?.let {
           viewModel.ledger(it, docId.toString())
        }

        viewModel?.loading?.observe(viewLifecycleOwner){
            if (it){
                baseActivity.showProgress()
            }
            else
                baseActivity.hideProgress()
        }

        viewModel.ledgerList.observe(viewLifecycleOwner) {
            mBinding.recyclerview.adapter = LedgerAdapter(it as ArrayList<Ledger>, object : OrderListener {
                override fun dataPass(position: Int?, v: View?, data: OrderList?) {
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        SingleOrderAdminFragment.getInstance(data?.id.toString()),
                        SingleOrderAdminFragment.TAG
                    )                }

            })
        }

        viewModel.paymentResponse.observe(viewLifecycleOwner) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy")
            mBinding.dateTitle.text = "Due Date: " + if ((it.totalamount ?: 0) == 0) "NA" else dateFormat.format(it.date)
            mBinding.priceTitle.text =  "Used Credit Amount: Rs. " + (it.totalamount ?: 0)
            mBinding.pendingPriceTitle.text =  "Pending Amount: Rs. " + (it.pendingamount ?: 0)
        }


    }

    private fun setListener(){


    }

}