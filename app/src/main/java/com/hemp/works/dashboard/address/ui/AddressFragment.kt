package com.hemp.works.dashboard.address.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.dashboard.model.RequestPayment
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.databinding.FragmentAddressBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.data.model.Address
import javax.inject.Inject


class AddressFragment : Fragment(), Injectable {

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
            //this.viewmodel = viewModel
            lifecycleOwner = this@AddressFragment
        }

        AddressFragmentDirections.actionAddressFragmentToPaymentFragment(RequestPayment())
        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddressFragment()
    }
}

interface AddressItemClickListener {

    fun onItemClick(address: Address)

    fun onEditClick(address: Address)

    fun onDeleteClick(address: Address)
}