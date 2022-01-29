package com.hemp.works.login.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OnboardingFragment1.newInstance()
            1 -> OnboardingFragment2.newInstance()
            else -> OnboardingFragment3.newInstance()
        }
    }

}