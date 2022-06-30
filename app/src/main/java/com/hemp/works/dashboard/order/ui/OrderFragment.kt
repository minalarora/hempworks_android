package com.hemp.works.dashboard.order.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.cart.ui.CartViewModel
import com.hemp.works.dashboard.home.ui.HomeFragmentDirections
import com.hemp.works.dashboard.order.ui.adapters.OrderAdapter
import com.hemp.works.dashboard.prescription.ui.PrescriptionFragmentDirections
import com.hemp.works.dashboard.prescription.ui.adapters.PrescriptionAdapter
import com.hemp.works.databinding.FragmentCartBinding
import com.hemp.works.databinding.FragmentOrderBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import java.util.*
import javax.inject.Inject


class OrderFragment : Fragment(), Injectable, OrderItemClickListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: OrderViewModel
    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentOrderBinding>(
            inflater, R.layout.fragment_order, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@OrderFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.orderRecyclerview.adapter = OrderAdapter(this)

        binding.filter.setOnClickListener {
            val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            showDatePicker()
        }

        binding.dateRangeClear.setOnClickListener {
            viewModel.updateDateRange(0, 0)
        }

        viewModel.orderList.observe(viewLifecycleOwner) {
            (binding.orderRecyclerview.adapter as OrderAdapter).submitList(it)
            viewModel.handleOrdersVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchOrders()
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
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
            viewModel.updateDateRange(startDateInMilliSeconds, endDateInMilliSeconds)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            OrderFragment()
    }

    override fun onOrderClick(orderId: Long) {
        OrderFragmentDirections.actionOrderFragmentToProductFragment(orderId.toString()).also {
            binding.root.findNavController().navigate(it)
        }
    }
}

interface OrderItemClickListener {
    fun onOrderClick(orderId: Long)
}