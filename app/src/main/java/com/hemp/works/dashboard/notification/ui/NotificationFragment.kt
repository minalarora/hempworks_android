package com.hemp.works.dashboard.notification.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.hemp.works.dashboard.prescription.ui.PrescriptionFragmentDirections
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.dashboard.prescription.ui.adapters.PrescriptionAdapter
import com.hemp.works.databinding.FragmentNotificationBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import java.util.*
import javax.inject.Inject

class NotificationFragment : Fragment(), Injectable{


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: NotificationViewModel
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentNotificationBinding>(
            inflater, R.layout.fragment_notification, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@NotificationFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.notificationRecyclerview.adapter = NotificationAdapter()

        viewModel.notificationList.observe(viewLifecycleOwner) {
            (binding.notificationRecyclerview.adapter as NotificationAdapter).submitList(it)
            viewModel.handleNotificationVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
           //NOTHING
        }

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            NotificationFragment()
    }

}
