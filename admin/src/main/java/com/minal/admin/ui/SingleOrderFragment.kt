package com.minal.admin.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestSingleOrder
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentSingleOrderBinding

class SingleOrderFragment: BaseFragment<FragmentSingleOrderBinding>() {

    private lateinit var viewModel : AdminViewModel
    private val mRequestSingleOrder by lazy { RequestSingleOrder() }
    var token:String?=null
    var docType:String?=null


    companion object {
        val TAG: String = SingleOrderFragment::class.java.simpleName
        fun getInstance(doc:String?) = SingleOrderFragment().also {
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
                           idTvOId.text = order.getOrNull(0)?.productid.toString()
                           idTvTime.text = createdAt
                           idTvNames.text = order.getOrNull(0)?.productname
                           idTvSize.text = order.getOrNull(0)?.variantname
                           idTvPrice.text = order.getOrNull(0)?.price.toString()
                           idTvStatus.text = status
                           idTvQuantity.text = order.getOrNull(0)?.quantity.toString()

                           idTvAddress.text = "${address.address1}, ${address.city}, ${address.pincode}, ${address.state}"


                       }
                   }

                }
            }
        }

    }

    private fun setListener() {

    }




}