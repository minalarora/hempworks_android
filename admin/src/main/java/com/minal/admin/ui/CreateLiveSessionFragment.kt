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
import com.minal.admin.data.request.RequestLiveSession
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateBlogBinding
import com.minal.admin.databinding.FragmentLiveSessionBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils

class CreateLiveSessionFragment : BaseFragment<FragmentLiveSessionBinding>() {

    private lateinit var viewModel : AdminViewModel
    var token:String?=null
    private val mRequestLiveSession by lazy { RequestLiveSession() }


    companion object {
        val TAG: String = CreateLiveSessionFragment::class.java.simpleName
        fun getInstance() = CreateLiveSessionFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentLiveSessionBinding {
        return FragmentLiveSessionBinding.inflate(inflater, container, false)
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



        viewModel?.liveSession?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{


                    baseActivity.showToast("You have successfully created Live session.")

                    baseActivity.onBackPressed()


                }
                is Result.Error ->
                {
                }

            }
        }

    }

    private fun setListener(){

        mBinding.idBtnLiveSession.setOnClickListener {
            if (mBinding.idEdtTitle.text.isNotEmpty() && mBinding.idEdtUrl.text.isNotEmpty()){
                mRequestLiveSession.apply {
                    url = mBinding.idEdtUrl.text.toString()
                    title = mBinding.idEdtTitle.text.toString()
                }
                viewModel?.liveSession(token,mRequestLiveSession)
            }
            else
            baseActivity.showToast("Please select and fill all fields.")
        }
    }
}