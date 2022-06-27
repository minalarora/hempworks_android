package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestAllDoctors
import com.minal.admin.data.request.RequestCreateCoupon
import com.minal.admin.data.request.RequestSingleDoctor
import com.minal.admin.data.response.ResponseAllDoctors
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAdminBinding
import com.minal.admin.databinding.FragmentDoctorListBinding
import com.minal.admin.ext_fun.addFragment
import com.minal.admin.ext_fun.hide
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.ext_fun.show
import com.minal.admin.utils.DoctorCardListener

class DoctorListFragment: BaseFragment<FragmentDoctorListBinding>(), DoctorCardListener{

    private lateinit var viewModel : AdminViewModel
    private val mRequestAllDoctors by lazy { RequestAllDoctors() }
    var token:String?=null
    var docType:String?=null

    private var  mResponseDoctor : List<ResponseAllDoctors>  =  ArrayList()


    private val mDoctorListAdapter by lazy {
        DoctorListAdapter(requireContext(),this)
    }

    companion object {
        val TAG: String = DoctorListFragment::class.java.simpleName
        fun getInstance(docType:String) = DoctorListFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_TYPE to docType)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentDoctorListBinding {
        return FragmentDoctorListBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        docType = arguments?.getString(BundleConstant.DOCTOR_TYPE)

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        mRequestAllDoctors.apply {
            status = docType.toString()
        }
        token?.let {
            viewModel?.getDoctors(it,mRequestAllDoctors)
        }

        viewModel?.allDoctors?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("data",it.data.toString())

                    if (!it.data.isNullOrEmpty()){
                        mResponseDoctor = it.data as ArrayList<ResponseAllDoctors>
                        mDoctorListAdapter.addItems(it.data)

                        mBinding.idRvItems.apply {
                            layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                            adapter = mDoctorListAdapter
                        }
                        mBinding.idTvNoResult.hide()

                    }
                    else
                    {
                        mBinding.idTvNoResult.show()
                    }


                    }
                }
            }
    }



    private fun setListener() {
        mBinding.idEdtSearch.doAfterTextChanged {
            // filter your list from your input
            filter(it.toString());
        }
    }

    override fun dataPass(position: Int?, v: View?, data: ResponseAllDoctors?) {

        when(v?.id){

            R.id.idRlDoctor->{
                replaceFragment(isAddToBackStack = true,
                    R.id.idFcvAdmin,
                    DoctorDetailFragment.getInstance(data?.id),
                    DoctorDetailFragment.TAG)
            }
        }
    }

    private fun filter(text: String) {

        if(text.isBlank()){
            mDoctorListAdapter.addItems(mResponseDoctor)
            return
        }
        val temp: MutableList<ResponseAllDoctors> = ArrayList()
        for (d in mDoctorListAdapter.getItemList()) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.name != null && d.name.toLowerCase().contains(text.toLowerCase())) {
                temp.add(d)
            }
        }
        //update recyclerview
        mDoctorListAdapter.addItems(temp)
    }

}