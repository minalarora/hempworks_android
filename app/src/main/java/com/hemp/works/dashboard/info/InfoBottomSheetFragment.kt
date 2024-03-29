package com.hemp.works.dashboard.info

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hemp.works.R
import com.hemp.works.databinding.FragmentInfoBottomSheetBinding

class InfoBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInfoBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentInfoBottomSheetBinding>(
            inflater, R.layout.fragment_info_bottom_sheet, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.text.text = InfoDialogFragmentArgs.fromBundle(requireArguments()).text
        binding.back.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                val d: BottomSheetDialog = dialog as BottomSheetDialog
                val bottomSheet: FrameLayout? = d.findViewById(R.id.design_bottom_sheet)
                if (bottomSheet != null) {
                    val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }, 0)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(text: String) =
            InfoBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT_KEY, text)
                }
            }
    }
}