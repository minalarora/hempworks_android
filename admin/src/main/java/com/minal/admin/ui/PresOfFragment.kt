package com.minal.admin.ui

import android.content.Intent
import android.net.Uri
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
import com.minal.admin.data.request.RequestPresList
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseAllDoctors
import com.minal.admin.data.response.ResponsePresList
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAllOrderBinding
import com.minal.admin.databinding.FragmentPrescripBinding
import com.minal.admin.ext_fun.addFragment
import com.minal.admin.utils.DoctorCardListener
import com.minal.admin.utils.FileUtils
import com.minal.admin.utils.OrderListener
import com.minal.admin.utils.PrescripListener

class PresOfFragment: BaseFragment<FragmentPrescripBinding>(), PrescripListener {

    private lateinit var viewModel : AdminViewModel
    private val mRequestPresList by lazy { RequestPresList() }
    var token:String?=null
    var docType:String?=null

    private val mPrescriptionAdapter by lazy {
        PrescriptionAdapter(requireContext(),this)
    }

    companion object {
        val TAG: String = PresOfFragment::class.java.simpleName
        fun getInstance(doc:String?) = PresOfFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to  doc)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentPrescripBinding {
        return FragmentPrescripBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {
        docType = arguments?.getString(BundleConstant.DOCTOR_ID)

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        mRequestPresList.apply {
            doctor = docType
        }
        token?.let {
            viewModel?.getPresList(it,mRequestPresList)
        }

        viewModel?.presList?.observe(viewLifecycleOwner) {
            Log.d("dat",it.toString())
            when (it) {
                is Result.Success -> {
                    Log.d("data",it.data.toString())

                    val expandedList = mutableListOf<ResponsePresList>()
                    for (prescriptionObject in it.data) {
                        if (prescriptionObject.prescriptions.isEmpty()) continue
                        for (prescriptionMedias in prescriptionObject.prescriptions) {
                            val prescription = prescriptionObject.copy(prescription = prescriptionMedias.prescription, type = prescriptionMedias.type)
                            expandedList.add(prescription)
                        }
                    }
                    mPrescriptionAdapter.addItems(expandedList)

                    mBinding.idRvOrders.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = mPrescriptionAdapter
                    }
                }
            }
        }

    }

    private fun setListener() {

    }

    override fun dataPass(position: Int?, v: View?, data: ResponsePresList?) {
        if (data?.type == "image"){
            startActivity(
                Intent(
                    Intent.ACTION_VIEW
                ).apply { setDataAndType(Uri.parse(data?.prescription), FileUtils.MIME_TYPES) }
            )
        }
        else{
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(data?.prescription)
                )
            )
        }

    }

}