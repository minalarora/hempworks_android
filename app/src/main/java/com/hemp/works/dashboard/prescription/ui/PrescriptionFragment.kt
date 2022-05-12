package com.hemp.works.dashboard.prescription.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorViewModel
import com.hemp.works.dashboard.prescription.ui.adapters.PrescriptionAdapter
import com.hemp.works.dashboard.search.ui.adapters.SearchAdapter
import com.hemp.works.databinding.FragmentDosageCalculatorBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import java.util.*
import javax.inject.Inject


class PrescriptionFragment : Fragment(), Injectable,
    androidx.appcompat.widget.SearchView.OnQueryTextListener, PrescriptionItemClickListener,
    androidx.appcompat.widget.SearchView.OnCloseListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: PrescriptionViewModel
    private lateinit var binding: FragmentPrescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentPrescriptionBinding>(
            inflater, R.layout.fragment_prescription, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@PrescriptionFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.prescriptionRecyclerview.adapter = PrescriptionAdapter(this)

        binding.filter.setOnClickListener {
            val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            showDatePicker()
        }

        binding.info.setOnClickListener {
            showInfo()
        }

        binding.search.setOnQueryTextListener(this)
        binding.search.setOnCloseListener(this)

        binding.create.setOnClickListener {
            PrescriptionFragmentDirections.actionPrescriptionFragmentToUploadPrescriptionFragment().let {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.dateRangeClear.setOnClickListener {
            viewModel.updateDateRange(0, 0)
        }

        viewModel.prescriptionList.observe(viewLifecycleOwner) {
            (binding.prescriptionRecyclerview.adapter as PrescriptionAdapter).submitList(it)
            viewModel.handlePrescriptionVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchPrescriptions()
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

    private fun showInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(
                HtmlCompat.fromHtml("<b>"+ resources.getString(R.string.disclaimer) +"</b>",
                    HtmlCompat.FROM_HTML_MODE_LEGACY)
            )
            .setMessage(
                HtmlCompat.fromHtml(getString(R.string.disclaimer_pres),
                    HtmlCompat.FROM_HTML_MODE_LEGACY))
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->

            }
            .show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PrescriptionFragment()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.updateDescription(newText)
        return false
    }

    override fun onItemClick(prescription: String?, type: String?) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(prescription)
            )
        )
    }

    override fun onClose(): Boolean {
        viewModel.updateDescription(null)
        return false
    }
}

interface PrescriptionItemClickListener{
    fun onItemClick(prescription: String?, type: String?)
}