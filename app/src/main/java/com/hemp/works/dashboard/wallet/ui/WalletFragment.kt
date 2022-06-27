package com.hemp.works.dashboard.wallet.ui

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
import com.hemp.works.dashboard.payment.ui.PaymentHistoryViewModel
import com.hemp.works.dashboard.payment.ui.adapters.PaymentHistoryAdapter
import com.hemp.works.dashboard.wallet.ui.adapters.WalletAdapter
import com.hemp.works.databinding.FragmentPaymentHistoryBinding
import com.hemp.works.databinding.FragmentWalletBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import java.util.*
import javax.inject.Inject

class WalletFragment : Fragment() , Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: WalletViewModel
    private lateinit var binding: FragmentWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentWalletBinding>(
            inflater, R.layout.fragment_wallet, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@WalletFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.orderRecyclerview.adapter = WalletAdapter()

        binding.filter.setOnClickListener {
            val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            showDatePicker()
        }

        binding.dateRangeClear.setOnClickListener {
            viewModel.updateDateRange(0, 0)
        }

        viewModel.walletHistory.observe(viewLifecycleOwner) {
            (binding.orderRecyclerview.adapter as WalletAdapter).submitList(it)
            viewModel.handleWalletListVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchWalletHistory()
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

    companion object {

        @JvmStatic
        fun newInstance() =
            WalletFragment()
    }
}