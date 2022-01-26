package com.hemp.works.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.R
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.databinding.FragmentVerifyMobileBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.login.ui.viewmodel.VerifyMobileViewModel
import javax.inject.Inject


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
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentVerifyMobileBinding>(
            inflater, R.layout.fragment_verify_mobile, container, false).apply {
            lifecycleOwner = this@VerifyMobileFragment
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            VerifyMobileFragment()
    }
}