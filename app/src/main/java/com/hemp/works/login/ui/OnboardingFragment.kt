package com.hemp.works.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.R
import com.hemp.works.databinding.FragmentOnboardingBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import javax.inject.Inject


class OnboardingFragment : Fragment() {



    private lateinit var binding: FragmentOnboardingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentOnboardingBinding>(
            inflater, R.layout.fragment_onboarding, container, false).apply {
            lifecycleOwner = this@OnboardingFragment
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnboardingFragment()
    }
}