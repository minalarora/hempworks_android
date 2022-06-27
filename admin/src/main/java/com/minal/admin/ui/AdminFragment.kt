package com.minal.admin.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.minal.admin.AdminWorkListener
import com.minal.admin.R
import com.minal.admin.databinding.FragmentAdminBinding
import com.minal.admin.ext_fun.replaceFragment

class AdminFragment : BaseFragment<FragmentAdminBinding>(),AdminWorkListener {

    var mLogout: Logout? = null

    fun updateApi(listener: Logout) {
        mLogout = listener
    }

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
        modelClasses.add(
            DataArray("All order list")

        )
//        modelClasses.add(
//
//            DataArray(R.drawable.common_full_open_on_phone,"1")
//
//        )


        var adapter = AdminAdapter(modelClasses,this)
        mBinding.topHeader.adapter = adapter

    }

    private fun setListener() {

        mBinding.idLlLogout.setOnClickListener {
            mLogout?.logoutAdmin()
        }
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
                else if (position == 5){
                    replaceFragment(isAddToBackStack = true,
                        R.id.idFcvAdmin,
                        AllOrderAdminFragment.getInstance(),
                        AllOrderAdminFragment.TAG)
                }
            }
        }
    }

}

interface Logout{
    fun logoutAdmin( )
}