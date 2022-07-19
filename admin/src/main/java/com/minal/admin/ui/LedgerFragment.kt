package com.minal.admin.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestBlog
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateBlogBinding
import com.minal.admin.databinding.FragmentLedgerBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils
import java.io.File

class LedgerFragment : BaseFragment<FragmentLedgerBinding>() {

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val mRequestBlog by lazy { RequestBlog() }


    private lateinit var viewModel : AdminViewModel
    var token:String?=null
    var imageFile: File?=null


    private  val PICK_FILE_REQUEST = 100
    private  val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 101

    companion object {
        val TAG: String = LedgerFragment::class.java.simpleName
        fun getInstance() = LedgerFragment()
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

        token?.let {
            viewModel?.getProductAll(it)
        }

        viewModel?.loading?.observe(viewLifecycleOwner){
            if (it){
                baseActivity.showProgress()
            }
            else
                baseActivity.hideProgress()
        }



    }

    private fun setListener(){


    }

}