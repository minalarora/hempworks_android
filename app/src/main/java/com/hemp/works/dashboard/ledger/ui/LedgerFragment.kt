package com.hemp.works.dashboard.ledger.ui

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
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.credit.ui.PendingCreditBottomSheet
import com.hemp.works.dashboard.home.ui.HomeFragmentDirections
import com.hemp.works.dashboard.ledger.ui.adapters.LedgerAdapter
import com.hemp.works.dashboard.model.RequestPayment
import com.hemp.works.dashboard.model.ResponsePendingAmount
import com.hemp.works.databinding.FragmentLedgerBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class LedgerFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: LedgerViewModel
    private lateinit var binding: FragmentLedgerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentLedgerBinding>(
            inflater, R.layout.fragment_ledger, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@LedgerFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.recyclerview.adapter = LedgerAdapter()

        binding.filter.setOnClickListener {
            val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            showDatePicker()
        }

        binding.dateRangeClear.setOnClickListener {
            viewModel.updateDateRange(0, 0)
        }

        binding.payNow.setOnClickListener {
            showPendingAmountBottomSheet(viewModel.paymentResponse.value!!)
        }
        viewModel.ledger.observe(viewLifecycleOwner) {
            (binding.recyclerview.adapter as LedgerAdapter).submitList(it)
            viewModel.handleLedgerListVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root

    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showDatePicker() {
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

    private fun showPendingAmountBottomSheet(response: ResponsePendingAmount) {

        if (response.totalamount == null ||
            response.totalamount == 0) {
            return
        }
        val dateFormat = SimpleDateFormat(Constants.ONLY_DATE_FORMAT);
        val date = dateFormat.format(response.date!!)

        PendingCreditBottomSheet.newInstance(response.totalamount.toString(), date, "LEDGER")
            .show(requireActivity().supportFragmentManager, PendingCreditBottomSheet.javaClass.simpleName)

        requireActivity().supportFragmentManager.setFragmentResultListener(getString(R.string.ledger_amount_title), this, object :
            FragmentResultListener {
            override fun onFragmentResult(requestKey: String, bundle: Bundle) {
                val amount  = bundle.getString("amount", "")
                LedgerFragmentDirections.actionLedgerFragmentToPaymentFragment(
                    RequestPayment(
                        payment = "DIRECT",
                        discountprice = amount.toInt()!!,
                        amount = amount.toInt()!!,
                        reason = "CREDIT")
                ).also {
                    binding.root.findNavController().navigate(it)
                }
            } })
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            LedgerFragment()
    }
}