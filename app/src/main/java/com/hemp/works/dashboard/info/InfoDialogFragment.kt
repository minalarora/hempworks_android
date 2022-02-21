package com.hemp.works.dashboard.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.hemp.works.R
import com.hemp.works.databinding.FragmentInfoDialogBinding

const val TEXT_KEY = "text"
class InfoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInfoDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentInfoDialogBinding>(
            inflater, R.layout.fragment_info_dialog, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.text.text = InfoDialogFragmentArgs.fromBundle(requireArguments()).text
        binding.back.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(text: String) =
            InfoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT_KEY, text)
                }
            }
    }
}