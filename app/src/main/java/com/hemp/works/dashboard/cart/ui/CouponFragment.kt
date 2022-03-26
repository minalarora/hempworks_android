package com.hemp.works.dashboard.cart.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.cart.ui.adapter.CartAdapter
import com.hemp.works.dashboard.cart.ui.adapter.CouponAdapter
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.databinding.FragmentCartBinding
import com.hemp.works.databinding.FragmentCouponBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.utils.onDone
import javax.inject.Inject


class CouponFragment : Fragment(), Injectable, CouponItemClickListener{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: CouponViewModel
    private lateinit var binding: FragmentCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCouponBinding>(
            inflater, R.layout.fragment_coupon, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@CouponFragment
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }

        binding.coupon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrBlank()) {
                    binding.couponContainer.error = null
                    binding.couponContainer.isEndIconVisible = true
                } else {
                    binding.couponContainer.isEndIconVisible = false
                }
            }

        })

        binding.couponContainer.setEndIconOnClickListener {
            if (binding.couponContainer.isEndIconVisible) {
                addCoupon()
            }
        }

        binding.coupon.onDone {
            addCoupon()
        }

        binding.couponRecyclerview.adapter = CouponAdapter(this)

        viewModel.couponsList.observe(viewLifecycleOwner) {
            (binding.couponRecyclerview.adapter as CouponAdapter).submitList(it)
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (!it) showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
            else {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(resources.getString(R.string.coupon_applied))
                    .setNeutralButton(getString(R.string.ok)) { dialog, which ->

                       //TODO: GOTO CART
                    }
                    .show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    private fun addCoupon() {
        if (binding.coupon.text.isNullOrBlank()) binding.couponContainer.error = getString(R.string.invalid_coupon)
        else viewModel.addCoupon(binding.coupon.text.toString())
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CouponFragment()
    }

    override fun onItemClick(coupon: Coupon) {
        viewModel.addCoupon(coupon.name)
    }
}

interface CouponItemClickListener {
    fun onItemClick(coupon: Coupon)

}