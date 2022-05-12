package com.hemp.works.dashboard.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.hemp.works.R
import com.hemp.works.databinding.FragmentPrivacyBinding
import com.hemp.works.databinding.FragmentTACBinding


class TACFragment : Fragment() {

    private lateinit var binding: FragmentTACBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        binding = DataBindingUtil.inflate<FragmentTACBinding>(
            inflater, R.layout.fragment_t_a_c, container, false).apply {
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }

        binding.text.text =  HtmlCompat.fromHtml( requireContext().getString(R.string.tac_desc), HtmlCompat.FROM_HTML_MODE_LEGACY)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TACFragment()
    }
}