package com.hemp.works.dashboard.profile.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hemp.works.R
import com.hemp.works.databinding.FragmentEditMobileBottomSheetBinding
import com.hemp.works.databinding.FragmentProfileBinding


class EditMobileBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditMobileBottomSheetBinding
    var isMobileState : ObservableBoolean = ObservableBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEditMobileBottomSheetBinding>(
            inflater, R.layout.fragment_edit_mobile_bottom_sheet, container, false).apply {
                lifecycleOwner = this@EditMobileBottomSheetFragment
                isMobileState = this@EditMobileBottomSheetFragment.isMobileState
        }

        binding.mobile.requestFocus()

        binding.verify.setOnClickListener {
            if (isMobileState.get()) {
                if (binding.mobile.text.toString().length == 10) {
                    val imm: InputMethodManager =
                        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    isMobileState.set(false)
                    requireActivity().supportFragmentManager.setFragmentResult(
                        getString(R.string.enter_otp),
                        Bundle().apply { putString("mobile", binding.mobile.text.toString()) }
                    )
                }
            } else {
                requireActivity().supportFragmentManager.setFragmentResult(
                    getString(R.string.update_mobile_number),
                    Bundle().apply { putString("mobile", binding.mobile.text.toString())
                            putString("otp", binding.otp.text.toString())}
                )
                dismiss()
            }
        }
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
        fun newInstance() =
            EditMobileBottomSheetFragment()
    }
}