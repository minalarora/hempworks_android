package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.hoobio.data.network.ApiResponse
import com.minal.admin.AdminWorkListener
import com.minal.admin.R
import com.minal.admin.data.RetrofitHelper
import com.minal.admin.data.remote.RestApi
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.remote.RetrofitClient
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.data.viewmodel.ViewModelFactory
import com.minal.admin.databinding.FragmentAdminBinding
import com.minal.admin.ext_fun.addFragment
import com.minal.admin.ext_fun.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection

class AdminFragment : BaseFragment<FragmentAdminBinding>(),AdminWorkListener {


    companion object {
        val TAG: String = AdminFragment::class.java.simpleName
        fun getInstance() = AdminFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAdminBinding {
        return FragmentAdminBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {


        var layoutManager = LinearLayoutManager(context)
        mBinding.topHeader.setHasFixedSize(true)
        layoutManager.orientation = RecyclerView.VERTICAL
        mBinding.topHeader.layoutManager = layoutManager

        val modelClasses: ArrayList<DataArray> = ArrayList<DataArray>()
        modelClasses.add(
            DataArray("Create Coupon")

        )
        modelClasses.add(
            DataArray("Approved Doctors")

        )
        modelClasses.add(
            DataArray("Rejected Doctors")

        )
        modelClasses.add(
            DataArray("Pending Doctors")

        )
        modelClasses.add(
            DataArray("User Chat List")

        )
//        modelClasses.add(
//            DataArray(R.drawable.common_full_open_on_phone,"1")
//
//        )
//        modelClasses.add(
//
//            DataArray(R.drawable.common_full_open_on_phone,"1")
//
//        )


        var adapter = AdminAdapter(modelClasses,this)
        mBinding.topHeader.adapter = adapter

    }

    private fun setListener() {

    }

    override fun dataPass(position: Int?, v: View?, data: DataArray?) {

        when(v?.id){

            R.id.cardViewImg->{
                if (position == 0){
                    replaceFragment(isAddToBackStack = true,
                    R.id.idFcvAdmin,
                    CreateCouponFragment.getInstance(),
                    CreateCouponFragment.TAG)
                }
                else if (position == 1){
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        DoctorListFragment.getInstance("approved"),
                        DoctorListFragment.TAG)
                }
                else if (position == 2){
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        DoctorListFragment.getInstance("rejected"),
                        DoctorListFragment.TAG)
                }
                else if (position == 3){
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        DoctorListFragment.getInstance("pending"),
                        DoctorListFragment.TAG)
                }
                else if (position == 4){
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        ChatListFragment.getInstance(),
                        ChatListFragment.TAG)
                }
            }
        }
    }

}