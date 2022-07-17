package com.minal.admin.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestOrderUpdate
import com.minal.admin.data.request.RequestSingleOrder
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentSingleOrderBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.hide
import com.minal.admin.ext_fun.show
import com.minal.admin.ext_fun.showToast

class SingleOrderAdminFragment: BaseFragment<FragmentSingleOrderBinding>() {

    private lateinit var viewModel : AdminViewModel
    private val mRequestSingleOrder by lazy { RequestSingleOrder() }
    private val mRequestOrderUpdate by lazy { RequestOrderUpdate() }

    var token:String?=null
    var docType:String?=null
    var orderStatusType:String?=null

    private val mSingleOrderListAdapter by lazy {
        SingleOrderListAdapter(requireContext())
    }

    companion object {
        val TAG: String = SingleOrderAdminFragment::class.java.simpleName
        fun getInstance(doc:String?) = SingleOrderAdminFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to  doc)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentSingleOrderBinding {
        return FragmentSingleOrderBinding
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

        mRequestSingleOrder.apply {
            id = docType
        }
        token?.let {
            viewModel?.getSingleOrder(it,mRequestSingleOrder)
        }

        viewModel?.singleOrder?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("data",it.data.toString())

                    it.data.apply {
                        mBinding.apply {
                            Glide.with(requireContext()).load(doctorobject.profile)
                                .into(object : CustomTarget<Drawable>() {
                                    override fun onLoadFailed(errorDrawable: Drawable?) {
                                        super.onLoadFailed(errorDrawable)
                                        Log.d("url", "error")
                                        idIgProfile.setImageResource(R.drawable.ic_photo)
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable,
                                        transition: Transition<in Drawable>?
                                    ) {
                                        idIgProfile.setImageDrawable(resource)
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {

                                    }
                                })
                            idTvName.text = doctorobject.name
                            idTvClinic.text = doctorobject.clinic
                            idTvId.text = id.toString()
                            idTvCreated.text = doctorobject.createdAt
                            idTvEmail.text = doctorobject.email
                            idTvMobile.text = doctorobject.mobile
                            idTvAddress.text = "${address.address1}, ${address.city}, ${address.pincode}, ${address.state}"
                            orderStatusType = status

                            if(it.data.order == null){
                                mBinding.idTvOrder.hide()
                            }
                            else{
                                mBinding.idTvOrder.show()

                            }

                            mSingleOrderListAdapter.addItems(it.data.order)

                            if (transaction == null){
                                idRlTransaction.hide()
                            }
                            else{
                                idRlTransaction.show()
                                idTvReference.text = transaction.referenceid.toString()
                                idTvAmount.text = transaction.amount.toString()
                                idTvStatus.text = transaction.status
                            }


                            if (it.data.status == "INITITATED"){
                                mBinding.idRbInitiated.isChecked = true
                            }
                            else if(it.data.status == "DISPATCHED"){
                                mBinding.idRbDispatched.isChecked = true

                            }
                            else if (it.data.status ==" COMPLETED"){
                                mBinding.idRbCompleted.isChecked = true

                            }
                            else if(it.data.status == "INVALID"){
                                mBinding.idRbInvalid.isChecked = true

                            }
                            else if (it.data.status == "REQUEST_CANCELLATION"){
                                mBinding.idRbRequestCancellation.isChecked = true

                            }
                            else if (it.data.status == "CANCELLED"){
                                mBinding.idRbCancel.isChecked = false

                            }

                            mBinding.idRvOrders.apply {
                                layoutManager =
                                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                                adapter = mSingleOrderListAdapter
                            }

                        }
                    }

                }
            }
        }

    }

    private fun setListener() {

        mBinding.idRbInitiated.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = true
                mBinding.idRbDispatched.isChecked = false
                mBinding.idRbCompleted.isChecked = false
                mBinding.idRbInvalid.isChecked = false
                mBinding.idRbRequestCancellation.isChecked = false
                mBinding.idRbCancel.isChecked = false

                statusChange("INITITATED")
            }
        }

        mBinding.idRbDispatched.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = false
                mBinding.idRbDispatched.isChecked = true
                mBinding.idRbCompleted.isChecked = false
                mBinding.idRbInvalid.isChecked = false
                mBinding.idRbRequestCancellation.isChecked = false
                mBinding.idRbCancel.isChecked = false

                statusChange("DISPATCHED")

            }
        }

        mBinding.idRbInitiated.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = false
                mBinding.idRbDispatched.isChecked = false
                mBinding.idRbCompleted.isChecked = true
                mBinding.idRbInvalid.isChecked = false
                mBinding.idRbRequestCancellation.isChecked = false
                mBinding.idRbCancel.isChecked = false

                statusChange("COMPLETED")

            }
        }

        mBinding.idRbInvalid.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = false
                mBinding.idRbDispatched.isChecked = false
                mBinding.idRbCompleted.isChecked = false
                mBinding.idRbInvalid.isChecked = true
                mBinding.idRbRequestCancellation.isChecked = false
                mBinding.idRbCancel.isChecked = false

                statusChange("INVALID")

            }
        }

        mBinding.idRbRequestCancellation.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = false
                mBinding.idRbDispatched.isChecked = false
                mBinding.idRbCompleted.isChecked = false
                mBinding.idRbInvalid.isChecked = false
                mBinding.idRbRequestCancellation.isChecked = true
                mBinding.idRbCancel.isChecked = false

                statusChange("REQUEST_CANCELLATION")

            }
        }

        mBinding.idRbCancel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbInitiated.isChecked = false
                mBinding.idRbDispatched.isChecked = false
                mBinding.idRbCompleted.isChecked = false
                mBinding.idRbInvalid.isChecked = false
                mBinding.idRbRequestCancellation.isChecked = false
                mBinding.idRbCancel.isChecked = true

                statusChange("CANCELLED")

            }
        }


    }


    private fun statusChange(type: String) {
        mRequestOrderUpdate.apply {
            status = type
        }

        token?.let {
            viewModel?.updateOrder(it, docType, mRequestOrderUpdate)
        }

        viewModel?.updateOrder?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    baseActivity.showToast("you changed order status.")
                }

                is Result.Error -> {


                }
            }
        }
    }
}