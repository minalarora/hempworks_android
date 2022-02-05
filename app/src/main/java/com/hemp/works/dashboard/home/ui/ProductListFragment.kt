package com.hemp.works.dashboard.home.ui

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
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.ProductAdapter
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
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentProductListBinding>(
            inflater, R.layout.fragment_product_list, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@ProductListFragment
        }

        binding.recyclerview.adapter = ProductAdapter()

        viewModel.productId = ProductListFragmentArgs.fromBundle(requireArguments()).product
        viewModel.fetchProductsByCategory(ProductListFragmentArgs.fromBundle(requireArguments()).category!!)

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.recyclerview.adapter as ProductAdapter).submitList(it)
            viewModel.handleAllProductVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            //TODO: SEND BACK TO DASHBOARD
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
        fun newInstance() = ProductListFragment()
    }
}