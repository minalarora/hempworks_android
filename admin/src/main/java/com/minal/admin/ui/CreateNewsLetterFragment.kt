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
import com.minal.admin.data.request.RequestNewsLetter
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateNewsLetterBinding
import com.minal.admin.databinding.FragmentCreateTutorialBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils
import java.io.File

class CreateNewsLetterFragment : BaseFragment<FragmentCreateNewsLetterBinding>() {

    private val mRequestNewsLetter by lazy { RequestNewsLetter() }

    private lateinit var viewModel : AdminViewModel
    var token:String?=null

    companion object {
        val TAG: String = CreateNewsLetterFragment::class.java.simpleName
        fun getInstance() = CreateNewsLetterFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentCreateNewsLetterBinding {
        return FragmentCreateNewsLetterBinding.inflate(inflater, container, false)
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


        viewModel?.newsLetter?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{

                    baseActivity.showToast("You have successfully created news letter.")

                    baseActivity.onBackPressed()



                }
                is Result.Error ->
                {
                }

            }
        }

    }

    private fun setListener() {


        mBinding.idBtnNewsLetter.setOnClickListener {
            if (mBinding.idEdtTitle.text.isNotEmpty() && mBinding.idEdtImage.text.isNotEmpty()
                && mBinding.idEdtPdf.text.isNotEmpty())
            {
                mRequestNewsLetter.apply {
                    title = mBinding.idEdtTitle.text.toString()
                    image = mBinding.idEdtImage.text.toString()
                    pdf = mBinding.idEdtPdf.text.toString()
                }
                viewModel?.newsLetter(token,mRequestNewsLetter)
            }
        }
    }

}