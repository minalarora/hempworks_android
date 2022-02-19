package com.hemp.works.dashboard.prescription.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorViewModel
import com.hemp.works.databinding.FragmentDosageCalculatorBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class PrescriptionFragment : Fragment(), Injectable {


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

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
        binding.root.findNavController().popBackStack()
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            PrescriptionFragment()
    }
}

interface PrescriptionItemClickListener{
    fun onItemClick(prescription: String?, type: String?)
}