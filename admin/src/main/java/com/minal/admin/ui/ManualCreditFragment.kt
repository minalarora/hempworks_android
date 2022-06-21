package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.ReqAddCreditManual
import com.minal.admin.data.request.RequestDocUpStatus
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentDoctorDetailBinding
import com.minal.admin.databinding.FragmentManualCreditBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.view.TextViewDatePicker

class ManualCreditFragment: BaseFragment<FragmentManualCreditBinding>() {

    private val mReqAddCreditManual by lazy { ReqAddCreditManual() }
    var docId:String?=null
    private lateinit var viewModel: AdminViewModel
    var token: String? = null
    var selectedDate:String?=null
    var price:String?=null


    private val mTimePicker by lazy {
        TextViewDatePicker(requireContext(), setMaxDate = true, setMinDate = false) { date ->
            selectedDate = date
            mBinding.idEdtDate.setText(CalendarUtils.getMonthName(date))
        }
    }

    companion object {
        val TAG: String = ManualCreditFragment::class.java.simpleName
        fun getInstance(docId: String?) = ManualCreditFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to docId)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentManualCreditBinding {
        return FragmentManualCreditBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {
        docId = arguments?.getString(BundleConstant.DOCTOR_ID)

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token = PreferenceManager.getDefaultSharedPreferences(context)
            ?.getString(RestConstant.AUTH_TOKEN, "").toString()

        viewModel?.creditManual?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    baseActivity.onBackPressed()
                }

                is Result.Error -> {


                }
            }
        }

    }

    private fun setListener() {

        mBinding.idEdtDate.setOnClickListener {
            mTimePicker.show()

        }

        mBinding.idEdtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                price = mBinding.idEdtAmount.text.toString()
            }
        })

         mBinding.idButton.setOnClickListener {

             mReqAddCreditManual.apply {
                 amount = price?.toInt()
                 date = "${selectedDate} ${"16:49:27"}"
                 doctor = docId
             }
             token?.let { viewModel.addCreditManual(it,mReqAddCreditManual) }

         }


    }

}