package com.minal.admin.ui

import android.icu.util.BuddhistCalendar
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestAllDoctors
import com.minal.admin.data.request.RequestOrderList
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAllOrderBinding
import com.minal.admin.databinding.FragmentDoctorDetailBinding
import com.minal.admin.ext_fun.addFragment
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.utils.OrderListener
import java.util.*

class AllOrderFragment: BaseFragment<FragmentAllOrderBinding>(),OrderListener  {

    private lateinit var viewModel : AdminViewModel
    private val mRequestOrderList by lazy { RequestOrderList() }
    var token:String?=null
    var docType:String?=null
    var orderId:String?=null

    private val mAllOrderAdapter by lazy {
        AllOrderAdapter(requireContext(),this)
    }

    companion object {
        val TAG: String = AllOrderFragment::class.java.simpleName
        fun getInstance(doc:String?) = AllOrderFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to  doc)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAllOrderBinding {
        return FragmentAllOrderBinding
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

        mRequestOrderList.apply {
            doctor = docType
        }
        token?.let {
            viewModel?.getOrderList(it,mRequestOrderList)
        }

        viewModel?.orderList.observe(viewLifecycleOwner) {
            Log.d("op","op")
            Log.d("op",it.toString())


            when (it) {

                is Result.Success -> {
                    Log.d("data",it.data.toString())
                    Log.d("op1","op")


                    mAllOrderAdapter.addItems(it.data)


                    orderId = it.data.getOrNull(0)?.id.toString()

                    mBinding.idRvOrders.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = mAllOrderAdapter
                    }

                }
            }
        }

    }

    private fun setListener() {

        mBinding.idFilter.setOnClickListener {
            showDatePicker()

        }
    }

    override fun dataPass(position: Int?, v: View?, data: OrderList?) {

        when(v?.id){

            R.id.idRlOrder->{
                addFragment(isAddToBackStack = true,
                    R.id.idFcvAdmin,
                    SingleOrderFragment.getInstance(orderId),
                    SingleOrderFragment.TAG
                )
            }
        }
    }

    private fun showDatePicker()
    {
        val calendar = Calendar.getInstance();
        calendar.time = Date()
        val max = calendar.timeInMillis;

        calendar.add(Calendar.YEAR, -6)
        val min = calendar.timeInMillis;

        // create the instance of the CalendarConstraints
        // Builder
        val calendarConstraintBuilder = CalendarConstraints.Builder()

        // and set the start and end constraints (bounds)
        calendarConstraintBuilder.setStart(min);
        calendarConstraintBuilder.setEnd(max);

        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(getString(R.string.select_dates))
            .setTheme(R.style.MaterialCalendarTheme)
            .setSelection(null)
            .setCalendarConstraints(calendarConstraintBuilder.build())
            .build()

        datePicker.show(requireActivity().supportFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener { selection: Pair<Long, Long>? ->
            val startDateInMilliSeconds  = selection?.first ?: 0
            val endDateInMilliSeconds  = selection?.second ?: 0
//            viewModel.updateDateRange(startDateInMilliSeconds, endDateInMilliSeconds)
        }
    }

}