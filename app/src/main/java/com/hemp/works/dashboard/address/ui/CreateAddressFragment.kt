package com.hemp.works.dashboard.address.ui

import android.app.Activity
import android.graphics.Color
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
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.product.ui.ProductFragmentArgs
import com.hemp.works.databinding.FragmentAddressBinding
import com.hemp.works.databinding.FragmentCreateAddressBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.data.model.Address
import javax.inject.Inject


class CreateAddressFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: CreateAddressViewModel
    private lateinit var binding: FragmentCreateAddressBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCreateAddressBinding>(
            inflater, R.layout.fragment_create_address, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@CreateAddressFragment
        }

        //ProductFragmentArgs.fromBundle(requireArguments()).id!!,
        //AddressFragmentDirections.actionAddressFragmentToPaymentFragment(RequestPayment())
        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.submit.setOnClickListener {
            viewModel.putAddress (
                addressLine1 = binding.addressLine1.text.toString(),
                addressLine2 = binding.addressLine2.text.toString(),
                city = binding.city.text.toString(),
                state = binding.state.selectedItem.toString(),
                pincode = binding.pincode.text.toString(),
                mobile = binding.mobile.text.toString()
            )
        }


        viewModel.fetchAddress(CreateAddressFragmentArgs.fromBundle(requireArguments()).id)
        viewModel.address.observe(viewLifecycleOwner) {
            if (it != null) {
                onExistingAddress(it)
            } else {
                onNewAddress()
            }
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (it) {
                if (viewModel.address.value != null) {
                    showSuccessSnackBar(getString(R.string.update_address_done))
                } else {
                    showSuccessSnackBar(getString(R.string.add_address_done))
                }
                binding.root.findNavController().popBackStack()
            } else {
                showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
            }
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

    private fun showSuccessSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(ContextCompat.getColor(view.context, R.color.orange_F8AA37))
            setTextColor(Color.BLACK)
        }.show()
    }

    private fun onNewAddress() {
        binding.title.text = getString(R.string.new_address_text)
        binding.submit.text = getString(R.string.add_address)
    }

    private fun onExistingAddress(address: Address) {
        binding.title.text = getString(R.string.update_address_text)
        binding.submit.text = getString(R.string.update_address)
        binding.addressLine1.setText(address.address1)
        binding.addressLine2.setText(address.address2)
        binding.city.setText(address.city)
        resources.getStringArray(R.array.state_list).toList().indexOf(address.state ?: getString(R.string.empty_string))?.let {
            if ( it >= 1) binding.state.setSelection(it)
        }
        binding.pincode.setText(address.pincode?.toString())
        binding.mobile.setText(address.mobile)

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateAddressFragment()
    }
}