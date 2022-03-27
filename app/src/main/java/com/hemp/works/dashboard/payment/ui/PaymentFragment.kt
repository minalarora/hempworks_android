package com.hemp.works.dashboard.payment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.R
import com.hemp.works.databinding.FragmentCreateBinding
import com.hemp.works.databinding.FragmentPaymentBinding
import com.hemp.works.di.Injectable
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.CreateViewModel
import javax.inject.Inject


class PaymentFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: PaymentViewModel
    private lateinit var binding: FragmentPaymentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PaymentFragment()
    }
}