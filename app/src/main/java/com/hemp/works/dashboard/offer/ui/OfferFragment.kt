package com.hemp.works.dashboard.offer.ui

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.cart.ui.CouponFragment
import com.hemp.works.dashboard.cart.ui.CouponItemClickListener
import com.hemp.works.dashboard.cart.ui.adapter.CouponAdapter
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.databinding.FragmentCouponBinding
import com.hemp.works.databinding.FragmentOfferBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class OfferFragment : Fragment(), Injectable, CouponItemClickListener{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: OfferViewModel
    private lateinit var binding: FragmentOfferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentOfferBinding>(
            inflater, R.layout.fragment_offer, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@OfferFragment
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }


        binding.couponRecyclerview.adapter = CouponAdapter(this, false)

        viewModel.couponsList.observe(viewLifecycleOwner) {
            (binding.couponRecyclerview.adapter as CouponAdapter).submitList(it)
        }


        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }



    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            OfferFragment()
    }

    override fun onItemClick(coupon: Coupon) {
        val clipboardManager = getSystemService(requireContext(), ClipboardManager::class.java) as ClipboardManager
        val clipData = ClipData.newPlainText(
            "coupon",
           coupon.name.toString()
        )
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }
}

