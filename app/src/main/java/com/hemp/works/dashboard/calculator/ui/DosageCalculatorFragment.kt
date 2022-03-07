package com.hemp.works.dashboard.calculator.ui

import android.app.Activity
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.info.InfoDialogFragment
import com.hemp.works.dashboard.info.InfoDialogFragmentDirections
import com.hemp.works.databinding.FragmentDosageCalculatorBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class DosageCalculatorFragment : Fragment() , Injectable, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: DosageCalculatorViewModel
    private lateinit var binding: FragmentDosageCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentDosageCalculatorBinding>(
            inflater, R.layout.fragment_dosage_calculator, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@DosageCalculatorFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.productSpinner.onItemSelectedListener = this
        viewModel.productList.observe(viewLifecycleOwner) {
            setAdapter(binding.productSpinner, it)
        }

        binding.typeSpinner.onItemSelectedListener = this
        viewModel.typeList.observe(viewLifecycleOwner) {
            setAdapter(binding.typeSpinner, it)
        }

        binding.indicationSpinner.onItemSelectedListener = this
        viewModel.indicationList.observe(viewLifecycleOwner) {
            setAdapter(binding.indicationSpinner, it)
        }

        binding.weightSpinner.onItemSelectedListener = this
        viewModel.weightList.observe(viewLifecycleOwner) {
            setAdapter(binding.weightSpinner, it)
        }

        viewModel.dosage.observe(viewLifecycleOwner) {
//            InfoDialogFragment.newInstance(it).show(childFragmentManager, InfoDialogFragment.javaClass.simpleName)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.result))
                .setMessage(createResult(it))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->

                }
                .show()
            binding.productSpinner.setSelection(0)
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

    private fun createResult(res: String): String {
        return  "Product: " + binding.productSpinner.selectedItem.toString() + "\n" + "\n" +
                "Product Type: " + binding.typeSpinner.selectedItem.toString() + "\n" + "\n" +
                "Indication: " + binding.indicationSpinner.selectedItem.toString() + "\n" + "\n" +
                "Weight: " + binding.weightSpinner.selectedItem.toString() + "\n" + "\n" +
                "Result: " + res
    }

    private fun setAdapter(spinner: Spinner, list: List<String>) {
        val arrayList = ArrayList<String>()
        arrayList.add( getString(R.string.select_any_value))
        arrayList.addAll(list)
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayList )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            DosageCalculatorFragment()
    }

    override fun onItemSelected(spinner: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem = spinner?.getItemAtPosition(p2).toString()
        if (selectedItem == getString(R.string.select_any_value)) return
       when(spinner as Spinner) {
           binding.productSpinner -> {
                viewModel.onProductSelected(selectedItem)
           }
           binding.typeSpinner -> {
                viewModel.onTypeSelected(selectedItem)
           }
           binding.indicationSpinner -> {
                viewModel.onIndicationSelected(selectedItem)
           }
           binding.weightSpinner -> {
               viewModel.onWeightSelected(selectedItem)
           }
       }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}