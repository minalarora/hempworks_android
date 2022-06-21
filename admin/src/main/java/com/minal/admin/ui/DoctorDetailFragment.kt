package com.minal.admin.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.minal.admin.data.request.RequestDocUpStatus
import com.minal.admin.data.request.RequestSingleDoctor
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentDoctorDetailBinding
import com.minal.admin.databinding.FragmentDoctorListBinding
import com.minal.admin.ext_fun.*
import com.minal.admin.utils.FileUtils

class DoctorDetailFragment: BaseFragment<FragmentDoctorDetailBinding>() {

    private val mRequestSingleDoctor by lazy { RequestSingleDoctor() }
    private val mRequestDocUpStatus by lazy { RequestDocUpStatus() }

    private lateinit var viewModel: AdminViewModel
    var token: String? = null
    var certificates: String? = null
    var docid: String? = null
    var doc: String? = null

    private val mDiscountAdapter by lazy {
        DiscountAdapter()
    }

    companion object {
        val TAG: String = DoctorDetailFragment::class.java.simpleName
        fun getInstance(docId: String?) = DoctorDetailFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to docId)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentDoctorDetailBinding {
        return FragmentDoctorDetailBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token = PreferenceManager.getDefaultSharedPreferences(context)
            ?.getString(RestConstant.AUTH_TOKEN, "").toString()

        doc = arguments?.getString(BundleConstant.DOCTOR_ID)

        mRequestSingleDoctor.apply {
            id = doc
        }
        token?.let {
            viewModel?.getDoctorDetail(it, mRequestSingleDoctor)
        }

        viewModel?.doctorDetail?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("data", it.data.toString())
                    mDiscountAdapter.addItems(it.data.discount)

                    it.data.apply {
                        mBinding.apply {
                            Glide.with(requireContext()).load(profile)
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

                            idTvName.text = name
                            idTvClinic.text = clinic
                            idTvEmail.text = email
                            idTvId.text = id
                            idTvMobile.text = mobile
                            certificates = certificate
                            docid = id

                            if (status == "approved") {
                             idRbApprove.isChecked = true
                            } else if (status == "rejected") {
                                idRbReject.isChecked = true

                            } else {
                                idRbPending.isChecked = true

                            }
                        }
                    }

                    mBinding.idRvItems.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = mDiscountAdapter
                    }
                }
                is Result.Error -> {


                }
            }
        }

        mBinding.idTvCertificate.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW
                ).apply { setDataAndType(Uri.parse(certificates), FileUtils.MIME_TYPES) }
            )
        }


    }

    private fun setListener() {

        mBinding.idTvManOrder.setOnClickListener {
            addFragment(
                isAddToBackStack = true,
                R.id.idFcvAdmin,
                ManualOrderFragment.getInstance(doc),
                ManualOrderFragment.TAG
            )
        }

        mBinding.idTvManCredit.setOnClickListener {
            addFragment(
                isAddToBackStack = true,
                R.id.idFcvAdmin,
                ManualCreditFragment.getInstance(doc),
                ManualCreditFragment.TAG
            )
        }

        mBinding.idTvOrder.setOnClickListener {
            addFragment(
                isAddToBackStack = true,
                R.id.idFcvAdmin,
                AllOrderFragment.getInstance(doc),
                AllOrderFragment.TAG
            )
        }

        mBinding.idTvPres.setOnClickListener {
            addFragment(
                isAddToBackStack = true,
                R.id.idFcvAdmin,
                PresOfFragment.getInstance(doc),
                PresOfFragment.TAG
            )
        }

        mBinding.idRbApprove.setOnClickListener {
            mBinding.idRbApprove.isChecked = true
            mBinding.idRbReject.isChecked = false
            mBinding.idRbPending.isChecked = false

            statusChange("approved")
        }

       mBinding.idRbReject.setOnClickListener {
           mBinding.idRbApprove.isChecked = false
           mBinding.idRbReject.isChecked = true
           mBinding.idRbPending.isChecked = false

           statusChange("rejected")

       }

       mBinding.idRbPending.setOnClickListener {
           mBinding.idRbApprove.isChecked = false
           mBinding.idRbReject.isChecked = false
           mBinding.idRbPending.isChecked = true

           statusChange("pending")
       }

    }

    private fun statusChange(type: String) {
        mRequestDocUpStatus.apply {
            status = type
        }
        token?.let {
            viewModel?.getUpdateStatus(it, doc, mRequestDocUpStatus)
        }

        viewModel?.doctorDetail?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    baseActivity.showToast("you changed doctor status.")
                }

                is Result.Error -> {


                }
            }
        }
    }
}