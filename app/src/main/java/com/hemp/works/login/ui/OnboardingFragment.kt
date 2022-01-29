package com.hemp.works.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.hemp.works.R
import com.hemp.works.databinding.FragmentOnboardingBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.onboarding.OnboardingPagerAdapter
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import javax.inject.Inject


class OnboardingFragment : Fragment() {



    private lateinit var binding: FragmentOnboardingBinding
    var isLast : ObservableBoolean = ObservableBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentOnboardingBinding>(
            inflater, R.layout.fragment_onboarding, container, false).apply {
            this.fragment = this@OnboardingFragment
            lifecycleOwner = this@OnboardingFragment
        }

        binding.viewpager.apply {
            adapter = OnboardingPagerAdapter(this@OnboardingFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
        }.attach()

        binding.next.setOnClickListener {
            if (binding.viewpager.currentItem == 2) {
                binding.root.findNavController().navigate(
                    OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
                )
            } else {
                if (binding.viewpager.currentItem == 1) {
                    isLast.set(true)
                }
                binding.viewpager.setCurrentItem(binding.viewpager.currentItem + 1, true)
            }
        }

        binding.skip.setOnClickListener {
            binding.root.findNavController().navigate(
                OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
            )
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnboardingFragment()
    }
}