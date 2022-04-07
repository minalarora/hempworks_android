package com.hemp.works.dashboard.cart.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.cart.ui.adapter.CartAdapter
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.databinding.FragmentCartBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class CartFragment : Fragment(), Injectable, CartItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCartBinding>(
            inflater, R.layout.fragment_cart, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@CartFragment
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }

        binding.cartRecyclerview.adapter = CartAdapter(this)

        binding.crossImageview.setOnClickListener {
            viewModel.removeCoupon()
        }

        binding.couponValue.setOnClickListener {
            CartFragmentDirections.actionCartFragmentToCouponFragment().also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.buyNow.setOnClickListener {
            CartFragmentDirections.actionCartFragmentToAddressFragment().also {
                binding.root.findNavController().navigate(it)
            }
        }

        viewModel.cartList.observe(viewLifecycleOwner) {
            (binding.cartRecyclerview.adapter as CartAdapter).submitList(it)
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            viewModel.fetchCartDetails()
            if (!it) showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        viewModel.fetchCartDetails()
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }

    override fun onItemClick(cartProduct: CartProduct) {
        CartFragmentDirections.actionCartFragmentToProductFragment(cartProduct.productid?.toString()).also {
            binding.root.findNavController().navigate(it)
        }
    }

    override fun onCancelClick(cartProduct: CartProduct) {
        viewModel.onCancelCart(cartProduct)
    }

    override fun onQuantityChange(cartProduct: CartProduct) {
        viewModel.onQuantityChange(cartProduct)
    }
}
interface CartItemClickListener {
    fun onItemClick(cartProduct: CartProduct)

    fun onCancelClick(cartProduct: CartProduct)

    fun onQuantityChange(cartProduct: CartProduct)
}