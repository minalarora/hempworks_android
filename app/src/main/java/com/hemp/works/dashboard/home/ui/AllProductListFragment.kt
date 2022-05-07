package com.hemp.works.dashboard.home.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.ProductAdapter
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.databinding.FragmentAllProductListBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject

class  AllProductListFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: AllProductListViewModel
    private lateinit var binding: FragmentAllProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentAllProductListBinding>(
            inflater, R.layout.fragment_all_product_list, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@AllProductListFragment
        }


        binding.recyclerview.adapter = ProductAdapter(object :
            ProductItemClickListener {
            override fun onProductClick(product: Product) {
                AllProductListFragmentDirections.actionAllProductListFragmentToProductFragment(product.id.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            selectBottomItem(it)
            true
        }

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.recyclerview.adapter as ProductAdapter).submitList(it)
            viewModel.handleAllProductVisibility(it.isEmpty())
        }


        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.products
    }

    private fun selectBottomItem(menuItem: MenuItem) {

        when(menuItem.itemId) {
            R.id.home -> {
                AllProductListFragmentDirections.actionAllProductListFragmentToHomeFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.products -> {

            }
            R.id.support -> {

            }
            R.id.account -> {

            }


            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllProductListFragment()
    }
}