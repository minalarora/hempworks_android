package com.hemp.works.login.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
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
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentOnboardingBinding>(
            inflater, R.layout.fragment_onboarding, container, false).apply {
            this.fragment = this@OnboardingFragment
            lifecycleOwner = this@OnboardingFragment
        }

        binding.viewpager.apply {
            adapter = OnboardingPagerAdapter(this@OnboardingFragment)
        }

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) isLast.set(true)
                else isLast.set(false)
            }

        })


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