package com.hemp.works.login.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject

class SplashFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentSplashBinding>(
            inflater, R.layout.fragment_splash, container, false).apply {
            lifecycleOwner = this@SplashFragment
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it.token.isNullOrBlank()) {
                onNoUserFound()
            } else {
                it.doctor?.let {
                    //TODO: GOTO DOCTOR DASHBOARD
                }
                it.admin?.let {
                    //TODO: GOTO ADMIN DASHBOARD
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            onNoUserFound()
        }

        return binding.root
    }

    private fun onNoUserFound() {
        if (PreferenceManagerUtil.getString(requireContext(), Constants.FIRST_USER_FLAG_KEY).equals(Constants.FIRST_USER_FLAG_VALUE)) {
            binding.root.findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        } else {
            PreferenceManagerUtil.putString(requireContext(), Constants.FIRST_USER_FLAG_KEY, Constants.FIRST_USER_FLAG_VALUE)
            binding.root.findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment())
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SplashFragment()
    }
}