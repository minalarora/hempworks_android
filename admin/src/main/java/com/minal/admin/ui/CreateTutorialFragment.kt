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
import com.minal.admin.data.request.RequestTutorial
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateTutorialBinding
import com.minal.admin.databinding.FragmentLiveSessionBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils

class CreateTutorialFragment : BaseFragment<FragmentCreateTutorialBinding>() {

    private lateinit var viewModel : AdminViewModel
    var token:String?=null
    var mediaType = "video"

    private val mRequestTutorial by lazy { RequestTutorial() }


    companion object {
        val TAG: String = CreateTutorialFragment::class.java.simpleName
        fun getInstance() = CreateTutorialFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentCreateTutorialBinding {
        return FragmentCreateTutorialBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi(){
        mBinding.idRbVideo.isChecked = true

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


        viewModel?.tutorialData?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{

                    baseActivity.showToast("You have successfully created tutorial.")

                    baseActivity.onBackPressed()


                }
                is Result.Error ->
                {
                }

            }
        }




    }

    private fun setListener(){

        mBinding.idRbVideo.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbVideo.isChecked = true
                mBinding.idRbPdf.isChecked = false
                mediaType = "video"
            }
        }

        mBinding.idRbPdf.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbVideo.isChecked = false
                mBinding.idRbPdf.isChecked = true
                mediaType = "pdf"
            }
        }

        mBinding.idBtnTutorial.setOnClickListener {

            if (mBinding.idEdtTitle.text.isNotEmpty() && mBinding.idEdtDescription.text.isNotEmpty()
                && mBinding.idEdtUrl.text.isNotEmpty() && mediaType != null)
            {
                mRequestTutorial.apply {
                    title = mBinding.idEdtTitle.text.toString()
                    description = mBinding.idEdtDescription.text.toString()
                    url = mBinding.idEdtUrl.text.toString()
                    type = mediaType
                }
                viewModel?.tutorial(token,mRequestTutorial)
            }

        }
    }
}