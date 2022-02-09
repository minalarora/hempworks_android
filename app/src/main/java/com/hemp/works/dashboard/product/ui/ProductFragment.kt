package com.hemp.works.dashboard.product.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.github.piasy.biv.BigImageViewer
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.LinePagerIndicatorDecoration
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.ProductAdapter
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.model.Variant
import com.hemp.works.dashboard.product.ui.adapters.ImageAdapter
import com.hemp.works.dashboard.product.ui.adapters.SizeAdapter
import com.hemp.works.databinding.FragmentProductBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class ProductFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var snapHelper: PagerSnapHelper
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: ProductViewModel
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentProductBinding>(
            inflater, R.layout.fragment_product, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@ProductFragment
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.cart -> {
                   //TODO: GOTO CART
                }
            }
            true
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        viewModel.loadProduct(sharedViewModel.user,
            sharedViewModel.userType,
            ProductFragmentArgs.fromBundle(requireArguments()).id!!,
            ProductFragmentArgs.fromBundle(requireArguments()).category!!)

        //IMAGE RECYCLERVIEW
        binding.imageRecyclerview.adapter = ImageAdapter(object :
            ProductImageClickListener {
            override fun onProductImage(url: String) {
                ProductFragmentDirections.actionProductFragmentToProductImageFragment(url).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.imageRecyclerview, false);
        snapHelper.attachToRecyclerView(binding.imageRecyclerview)
        binding.imageRecyclerview.addItemDecoration(LinePagerIndicatorDecoration())

        viewModel.productImages.observe(viewLifecycleOwner) {
            BigImageViewer.prefetch(*it.map { image -> Uri.parse(image)  }.toTypedArray())
            (binding.imageRecyclerview.adapter as ImageAdapter).submitList(it)
        }

        //SIZE
        binding.sizeRecyclerview.adapter = SizeAdapter(object :
            SizeItemClickListener {
            override fun onSizeClick(variant: Variant) {
                viewModel.selectVariant(variant)
            }

        })

        binding.extraRecyclerview.adapter = ProductAdapter(object :
            ProductItemClickListener {
            override fun onProductClick(product: Product) {
                ProductFragmentDirections.actionProductFragmentToProductFragment(product.id.toString(), product.category.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.extraRecyclerview, false);

        binding.pdfDownload.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(viewModel.product.value?.pdf!!)
                )
            )
        }

        viewModel.sizes.observe(viewLifecycleOwner) {
            (binding.sizeRecyclerview.adapter as SizeAdapter).submitList(it)
        }

        viewModel.productsByCategory.observe(viewLifecycleOwner) {
            (binding.extraRecyclerview.adapter as ProductAdapter).submitList(it)
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
        binding.root.findNavController().popBackStack()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}

interface ProductItemClickListener{
    fun onProductClick(id: Product)
}

interface SizeItemClickListener{
    fun onSizeClick(variant: Variant)
}

interface ProductImageClickListener{
    fun onProductImage(url: String)
}
//https://blog.davidmedenjak.com/android/2017/06/24/viewpager-recyclerview.html