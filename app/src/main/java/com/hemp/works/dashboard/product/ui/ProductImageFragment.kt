package com.hemp.works.dashboard.product.ui


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideCustomImageLoader

import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.databinding.FragmentProductImageBinding
import com.hemp.works.di.Injectable
import javax.inject.Inject


class ProductImageFragment : Fragment(), Injectable {



    private lateinit var binding: FragmentProductImageBinding
    @Inject
    lateinit var applicationContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black);

        binding = DataBindingUtil.inflate<FragmentProductImageBinding>(
            inflater, R.layout.fragment_product_image, container, false).apply {
            lifecycleOwner = this@ProductImageFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.image.showImage(Uri.parse(ProductImageFragmentArgs.fromBundle(requireArguments()).url))
        //

        return binding.root
    }

    override fun onDestroyView() {
        binding.image.cancel()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductImageFragment()
    }
}

//https://blog.davidmedenjak.com/android/2017/06/24/viewpager-recyclerview.html