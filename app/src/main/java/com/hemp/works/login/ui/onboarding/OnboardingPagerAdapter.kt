package com.hemp.works.login.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OnboardingFragment1.newInstance()
            1 -> OnboardingFragment2.newInstance()
            2 -> OnboardingFragment3.newInstance()
            else -> OnboardingFragment4.newInstance()
        }
    }

}