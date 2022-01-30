package com.hemp.works.login.ui

import android.app.Activity
import android.content.Context
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
import com.hemp.works.databinding.FragmentForgetPasswordBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.ForgetPasswordViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import javax.inject.Inject


class ForgetPasswordFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: ForgetPasswordViewModel
    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentForgetPasswordBinding>(
            inflater, R.layout.fragment_forget_password, container, false).apply {
            lifecycleOwner = this@ForgetPasswordFragment
        }

        viewModel.mobile = ForgetPasswordFragmentArgs.fromBundle(requireArguments()).mobile!!

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.submit.setOnClickListener { viewModel.updatePassword(
            binding.password.text.toString(),
            binding.confirmPassword.text.toString()
        ) }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (it) {
                showSnackBar(getString(R.string.password_updated_successfully))
                binding.root.findNavController().popBackStack()
            } else {
                showSnackBar(getString(R.string.something_went_wrong))
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

    companion object {

        @JvmStatic
        fun newInstance() =
            ForgetPasswordFragment()

    }
}