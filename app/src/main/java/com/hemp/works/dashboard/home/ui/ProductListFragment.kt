package com.hemp.works.dashboard.home.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.BannerAdapter
import com.hemp.works.dashboard.home.ui.adapters.ProductAdapter
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.dashboard.search.ui.adapters.SearchAdapter
import com.hemp.works.databinding.FragmentProductListBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class  ProductListFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: ProductListViewModel
    private lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentProductListBinding>(
            inflater, R.layout.fragment_product_list, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@ProductListFragment
        }

        binding.bannerRecyclerview.adapter = BannerAdapter()
        ViewCompat.setNestedScrollingEnabled(binding.bannerRecyclerview, false);

        binding.recyclerview.adapter = ProductAdapter(object :
            ProductItemClickListener {
            override fun onProductClick(product: Product) {
                ProductListFragmentDirections.actionProductListFragmentToProductFragment(product.id.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.recyclerview, false);

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        viewModel.productId = ProductListFragmentArgs.fromBundle(requireArguments()).product
        viewModel.fetchProductsByCategory(ProductListFragmentArgs.fromBundle(requireArguments()).category!!)

        viewModel.bannerList.observe(viewLifecycleOwner) {
            (binding.bannerRecyclerview.adapter as BannerAdapter).submitList(it)
            viewModel.handleBannerVisibility(it.isEmpty())
            if (it.isNotEmpty()) viewModel.startScrolling()
        }

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.recyclerview.adapter as ProductAdapter).submitList(it)
            viewModel.handleAllProductVisibility(it.isEmpty())
            if (it.isNotEmpty()) viewModel.setHeader(it[0])
        }

        viewModel.scroll.observe(viewLifecycleOwner) {
            binding.bannerRecyclerview.smoothScrollToPosition(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            viewModel.handleAllProductVisibility(true)
            viewModel.handleBannerVisibility(true)
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
        fun newInstance() = ProductListFragment()
    }
}