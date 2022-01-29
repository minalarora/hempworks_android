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
import com.hemp.works.databinding.FragmentLoginBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.LoginViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject


class LoginFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@LoginFragment
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->

            if (user.token.isNullOrBlank()) {
                showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
            } else {
                user.doctor?.let {
                    PreferenceManagerUtil.putString(requireContext(), Constants.AUTH_TOKEN, user.token)
                    PreferenceManagerUtil.putString(requireContext(), Constants.USER_TYPE, Constants.DOCTOR)
                    //TODO: GOTO DOCTOR DASHBOARD
                }
                user.admin?.let {
                    PreferenceManagerUtil.putString(requireContext(), Constants.AUTH_TOKEN, user.token)
                    PreferenceManagerUtil.putString(requireContext(), Constants.USER_TYPE, Constants.ADMIN)
                    //TODO: GOTO ADMIN DASHBOARD
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        binding.login.setOnClickListener {
            viewModel.onLogin(binding.username.text.toString(), binding.password.text.toString())
        }

        binding.createAccount.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToVerifyMobileFragment(Constants.REASON_CREATE_ACCOUNT).also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.forgotPassword.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToVerifyMobileFragment(Constants.REASON_UPDATE_PASSWORD).also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.skipLogin.setOnClickListener {
            //TODO: GOTO DOCTOR DASHBOARD
        }

        return binding.root
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment()

    }
}