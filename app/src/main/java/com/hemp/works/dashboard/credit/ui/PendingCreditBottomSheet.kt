package com.hemp.works.dashboard.credit.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hemp.works.R
import com.hemp.works.databinding.FragmentPendingCreditBottomSheetBinding


private const val AMOUNT = "amount"
private const val DATE = "date"
private const val FROM = "from"

class PendingCreditBottomSheet : BottomSheetDialogFragment() {

    private var amount: String? = null
    private var date: String? = null
    private var from: String? = null

    private lateinit var binding: FragmentPendingCreditBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = it.getString(AMOUNT)
            date = it.getString(DATE)
            from = it.getString(FROM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentPendingCreditBottomSheetBinding>(
            inflater, R.layout.fragment_pending_credit_bottom_sheet, container, false).apply {
            lifecycleOwner = this@PendingCreditBottomSheet
        }

        binding.title.text = if (from == "HOME") getString(R.string.pending_amount_title, amount, date)
                              else  getString(R.string.ledger_amount_title, amount, date)


        binding.amount.setText(amount.toString())

        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrBlank()) {
                    binding.amountContainer.error = null
                }
            }

        })

        binding.pay.setOnClickListener {
            try {
                if (binding.amount.text.isNullOrBlank()) binding.amountContainer.error = getString(R.string.invalid_amount)
                else if (!binding.amount.text.toString().isDigitsOnly()) binding.amountContainer.error = getString(R.string.invalid_amount)
                else if (binding.amount.text.toString().toInt() <= 0) binding.amountContainer.error = getString(R.string.invalid_amount)
                else if (binding.amount.text.toString().toInt() > amount!!.toInt()) binding.amountContainer.error = getString(R.string.invalid_amount)
                else {
                    if (from == "HOME") {
                        requireActivity().supportFragmentManager.setFragmentResult(
                            getString(R.string.pending_amount_title),
                            Bundle().apply { putString("amount", binding.amount.text.toString()) }
                        )
                    } else {
                        requireActivity().supportFragmentManager.setFragmentResult(
                            getString(R.string.ledger_amount_title),
                            Bundle().apply { putString("amount", binding.amount.text.toString()) }
                        )
                    }
                    dismiss()
                }
            } catch (ex: Exception) {

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
        fun newInstance(amount: String, date: String, from: String) =
            PendingCreditBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(AMOUNT, amount)
                    putString(DATE, date)
                    putString(FROM, from)
                }
            }
    }
}