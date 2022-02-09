package com.hemp.works.dashboard.search.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.ProductAdapter
import com.hemp.works.dashboard.product.ui.ProductViewModel
import com.hemp.works.dashboard.search.ui.adapters.SearchAdapter
import com.hemp.works.databinding.FragmentProductBinding
import com.hemp.works.databinding.FragmentSearchBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater, R.layout.fragment_search, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@SearchFragment
        }

        binding.productRecyclerview.adapter = SearchAdapter(listener = object : SearchItemClickListener{
            override fun onItemClick(productid: Long, categoryid: Long) {
                SearchFragmentDirections.
                actionSearchFragmentToProductListFragment(productid.toString(), categoryid.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.search(p0.toString())
            }

        })

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.productRecyclerview.adapter as SearchAdapter).submitList(it)
            viewModel.handleAllProductVisibility(it.isEmpty())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            viewModel.handleAllProductVisibility(true)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showSoftKeyboard(binding.search)

    }

    override fun onPause() {
        super.onPause()
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}

interface SearchItemClickListener{
    fun onItemClick(productid: Long, categoryid: Long)
}