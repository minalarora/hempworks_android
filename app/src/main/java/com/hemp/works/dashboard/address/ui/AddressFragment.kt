package com.hemp.works.dashboard.address.ui

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.address.ui.adapter.AddressAdapter
import com.hemp.works.dashboard.cart.ui.adapter.CartAdapter
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.dashboard.model.RequestPayment
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.databinding.FragmentAddressBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.data.model.Address
import javax.inject.Inject


class AddressFragment : Fragment(), Injectable, AddressItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: AddressViewModel
    private lateinit var binding: FragmentAddressBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentAddressBinding>(
            inflater, R.layout.fragment_address, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@AddressFragment
        }

        //AddressFragmentDirections.actionAddressFragmentToPaymentFragment(RequestPayment())
        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.addressRecyclerview.adapter = AddressAdapter(this)

        binding.addAddress.setOnClickListener {
            AddressFragmentDirections.actionAddressFragmentToCreateAddressFragment(null)?.also {
                binding.root.findNavController().navigate(it)
            }
        }

        viewModel.addressList.observe(viewLifecycleOwner) {
            (binding.addressRecyclerview.adapter as AddressAdapter).submitList(it)
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            viewModel.fetchAddressList()
            if (!it) showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
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

    override fun onStart() {
        super.onStart()
        viewModel.fetchAddressList()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddressFragment()
    }

    override fun onItemClick(address: Address) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.mode_of_payment))
            .setMessage(getString(R.string.payment_dialog_text))
            .setNegativeButton(getString(R.string.credit_dialog_text)) { dialog, which ->
                AddressFragmentDirections.actionAddressFragmentToPaymentFragment(
                    RequestPayment(address = address.id,
                    payment = "CREDIT",
                    totalprice = viewModel.totalprice?.toInt()!!,
                    discountprice = viewModel.discountprice?.toInt()!!,
                    reason = "ORDER")
                ).also {
                    binding.root.findNavController().navigate(it)
                }
            }
            .setPositiveButton(getString(R.string.pay_now)) { dialog, which ->
                AddressFragmentDirections.actionAddressFragmentToPaymentFragment(
                    RequestPayment(address = address.id,
                        payment = "DIRECT",
                        totalprice = viewModel.totalprice?.toInt()!!,
                        discountprice = viewModel.discountprice?.toInt()!!,
                        reason = "ORDER")
                ).also {
                    binding.root.findNavController().navigate(it)
                }
            }
            .show()
    }

    override fun onEditClick(address: Address) {
        AddressFragmentDirections.actionAddressFragmentToCreateAddressFragment(address.id.toString())?.also {
            binding.root.findNavController().navigate(it)
        }
    }

    override fun onDeleteClick(address: Address) {
        viewModel.removeAddress(address)
    }
}

interface AddressItemClickListener {

    fun onItemClick(address: Address)

    fun onEditClick(address: Address)

    fun onDeleteClick(address: Address)
}