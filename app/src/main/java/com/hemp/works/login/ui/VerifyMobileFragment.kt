package com.hemp.works.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.databinding.FragmentVerifyMobileBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.login.ui.viewmodel.VerifyMobileViewModel
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


class VerifyMobileFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: VerifyMobileViewModel
    private lateinit var binding: FragmentVerifyMobileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        viewModel.reason = VerifyMobileFragmentArgs.fromBundle(requireArguments()).reason!!

        binding = DataBindingUtil.inflate<FragmentVerifyMobileBinding>(
            inflater, R.layout.fragment_verify_mobile, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@VerifyMobileFragment
        }

        viewModel.updateTitle()

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            viewModel.handleBooleanResponse(it)
        }

        viewModel.goAhead.observe(viewLifecycleOwner) {
            if (it) {
              val action =  if (viewModel.reason == Constants.REASON_CREATE_ACCOUNT)
                  VerifyMobileFragmentDirections.actionVerifyMobileFragmentToCreateFragment(binding.mobile.text.toString())
                else VerifyMobileFragmentDirections.actionVerifyMobileFragmentToForgetPasswordFragment(binding.mobile.text.toString())

                binding.root.findNavController().navigate(action)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.isMobileState.observe(viewLifecycleOwner) {
                val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.verify.setOnClickListener {
            if (viewModel.isMobileState.value == true) {
                viewModel.verifyMobile(binding.mobile.text.toString())
            } else {
                viewModel.verifyOtp(binding.mobile.text.toString(), binding.otp.text.toString())
            }
        }

        binding.resendOtp.setOnClickListener {
            if (viewModel.canResendOTP) viewModel.verifyMobile(binding.mobile.text.toString())
        }

        return binding.root
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }



    companion object {

        @JvmStatic
        fun newInstance() =
            VerifyMobileFragment()
    }
}