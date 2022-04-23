package com.hemp.works.login.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardActivity
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.utils.PreferenceManagerUtil
import com.minal.admin.AdminActivity
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
                it.doctor?.let { doctor ->
                    PreferenceManagerUtil.putDoctor(requireContext(), doctor)
                    Intent(requireActivity(), DashboardActivity::class.java).apply {
                        startActivity(this)
                        requireActivity().finish()
                    }
                }
                it.admin?.let { admin ->
                    PreferenceManagerUtil.putAdmin(requireContext(), admin)
                    Intent(requireActivity(), AdminActivity::class.java).apply {
                        startActivity(this)
                        requireActivity().finish()
                    }
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            onNoUserFound()
        }

        return binding.root
    }

    private fun onNoUserFound() {
        PreferenceManagerUtil.clear(requireContext())
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