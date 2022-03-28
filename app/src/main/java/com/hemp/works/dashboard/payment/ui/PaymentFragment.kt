package com.hemp.works.dashboard.payment.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.cashfree.pg.CFPaymentService
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.BuildConfig
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.model.Payment
import com.hemp.works.dashboard.model.RequestPayment
import com.hemp.works.databinding.FragmentCartBinding
import com.hemp.works.databinding.FragmentCreateBinding
import com.hemp.works.databinding.FragmentPaymentBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.CreateViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class PaymentFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: PaymentViewModel
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentPaymentBinding>(
            inflater, R.layout.fragment_payment, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@PaymentFragment
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }

        viewModel.requestPayment = PaymentFragmentArgs.fromBundle(requireArguments()).payment
        viewModel.initializePayment()

        viewModel.payment.observe(viewLifecycleOwner) { payment ->
            viewModel.requestPayment?.let {
                if (it.reason == "ORDER") {
                    if (it.payment == "DIRECT") {
                       openCashFreeSDK(payment, it)
                    } else if (it.payment == "CREDIT") {
                        if (payment.success == true) {
                           viewModel.updatePaymentStatus(PaymentStatus.ORDER_COMPLETED(
                               it.discountprice!!, payment.id!!
                           ))
                        } else {
                            viewModel.updatePaymentStatus(PaymentStatus.ORDER_FAILED(
                                it.discountprice!!, payment.id!!
                            ))
                        }
                    }
                } else {
                    openCashFreeSDK(payment, it)
                }
            }

        }

        viewModel.order.observe(viewLifecycleOwner) {
                viewModel.updatePaymentStatus(PaymentStatus.ORDER_COMPLETED(
                    viewModel.requestPayment?.discountprice!!, it.id.toString()))
        }

        viewModel.creditHistory.observe(viewLifecycleOwner) {
            viewModel.updatePaymentStatus(PaymentStatus.CREDIT_COMPLETED(
                viewModel.requestPayment?.discountprice!!, it.id.toString()))
        }

        viewModel.paymentStatus.observe(viewLifecycleOwner) {
            when(it) {
                is PaymentStatus.NONE -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.orange_F8AA37)

                is PaymentStatus.CREDIT_PENDING -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.yellow)

                is PaymentStatus.ORDER_PENDING -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.yellow)

                is PaymentStatus.CREDIT_COMPLETED -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.green)

                is PaymentStatus.ORDER_COMPLETED -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.green)

                is PaymentStatus.CREDIT_FAILED -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.red)

                is PaymentStatus.ORDER_FAILED -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.red)

            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == Constants.PAYMENT_ISSUE) {
                showSnackBar(it)
            }
        }

        sharedViewModel.paymentBundle.observe(viewLifecycleOwner) {
            onPaymentResult(it)
        }
        return binding.root
    }

    //
//[hempworks-web] [2022-03-19 09:44:39] server is up on port 8080
//[hempworks-web] [2022-03-19 10:21:29] response_post {
//    [hempworks-web] [2022-03-19 10:21:29]   orderId: 'Order000123',
//    [hempworks-web] [2022-03-19 10:21:29]   orderAmount: '1.00',
//    [hempworks-web] [2022-03-19 10:21:29]   referenceId: '826748288',
//    [hempworks-web] [2022-03-19 10:21:29]   txStatus: 'SUCCESS',
//    [hempworks-web] [2022-03-19 10:21:29]   paymentMode: 'UPI',
//    [hempworks-web] [2022-03-19 10:21:29]   txMsg: '00::Transaction is Successful',
//    [hempworks-web] [2022-03-19 10:21:29]   txTime: '2022-03-19 15:51:03',
//    [hempworks-web] [2022-03-19 10:21:29]   signature: '5q+Lrno3V9CZQ32HbH0AjnoX2p4DoozXB0NUcyCdPlQ='
//    [hempworks-web] [2022-03-19 10:21:29] }

    //
//2022-03-19 15:55:43.356 27860-27860/com.minal.cashfreesampleapp D/PAYMENT_TESTING: API Response :
//2022-03-19 15:55:43.356 27860-27860/com.minal.cashfreesampleapp D/PAYMENT_TESTING: orderId : Order000123
//2022-03-19 15:55:43.356 27860-27860/com.minal.cashfreesampleapp D/PAYMENT_TESTING: type : CashFreeResponse
//2022-03-19 15:55:43.356 27860-27860/com.minal.cashfreesampleapp D/PAYMENT_TESTING: txStatus : CANCELLED
    private fun onPaymentResult(bundle: Bundle?) {

        if (bundle != null) {
            if (bundle.getString(getString(R.string.txStatus)) == getString(R.string.success)) {

                if (viewModel.requestPayment?.reason == "ORDER") {
                    viewModel.updatePaymentStatus(PaymentStatus.ORDER_PENDING(
                        viewModel.requestPayment?.discountprice!!, bundle.getString(getString(R.string.orderId))!!))

                    viewModel.startValidateOrder()
                } else {
                    viewModel.updatePaymentStatus(PaymentStatus.CREDIT_PENDING(
                        viewModel.requestPayment?.amount!!, bundle.getString(getString(R.string.orderId))!!))

                    viewModel.startValidateCredit()
                }
            } else if (bundle.getString(getString(R.string.txStatus)) == getString(R.string.pending).uppercase(Locale.getDefault())) {

                if (viewModel.requestPayment?.reason == "ORDER") {
                    viewModel.updatePaymentStatus(PaymentStatus.ORDER_PENDING(
                        viewModel.requestPayment?.discountprice!!, bundle.getString(getString(R.string.orderId))!!))

                    viewModel.startValidateOrder()
                } else {
                    viewModel.updatePaymentStatus(PaymentStatus.CREDIT_PENDING(
                        viewModel.requestPayment?.amount!!, bundle.getString(getString(R.string.orderId))!!))

                    viewModel.startValidateCredit()
                }

            } else {
                if (viewModel.requestPayment?.reason == "ORDER") {
                    viewModel.updatePaymentStatus(PaymentStatus.ORDER_FAILED(
                        viewModel.requestPayment?.discountprice!!, bundle.getString(getString(R.string.orderId))!!))

                } else {
                    viewModel.updatePaymentStatus(PaymentStatus.CREDIT_FAILED(
                        viewModel.requestPayment?.amount!!, bundle.getString(getString(R.string.orderId))!!))

                }
            }
        } else {
            showSnackBar(Constants.PAYMENT_ISSUE)
        }
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
        binding.root.findNavController().popBackStack()
    }

    private fun openCashFreeSDK(responsePayment: Payment, requestPayment: RequestPayment) {
        val stage = "PROD"
        val token = responsePayment.token
        val cfPaymentService = CFPaymentService.getCFPaymentServiceInstance()
        cfPaymentService.setOrientation(0)
        cfPaymentService.doPayment(requireActivity(), getInputParams(responsePayment.id, if (BuildConfig.DEBUG) "1" else requestPayment.discountprice.toString(), requestPayment.reason ), token, stage, "#f8aa37", "#FFFFFF", true);
        //cfPaymentService.upiPayment(this, getInputParams(), token, stage);

    }

    private fun getInputParams(orderId: String?, orderAmount: String?, orderNote: String?): Map<String, String>? {

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        val appId = BuildConfig.CASHFREE_APP_ID
        val customerName = sharedViewModel.user?.name!!
        val customerPhone = sharedViewModel.user?.mobile!!
        val customerEmail = sharedViewModel.user?.email!!

        val params: MutableMap<String, String> = HashMap()
        params[CFPaymentService.PARAM_APP_ID] = appId
        params[CFPaymentService.PARAM_ORDER_ID] = orderId!!
        params[CFPaymentService.PARAM_ORDER_AMOUNT] = orderAmount!!
        params[CFPaymentService.PARAM_ORDER_NOTE] = orderNote!!
        params[CFPaymentService.PARAM_CUSTOMER_NAME] = customerName
        params[CFPaymentService.PARAM_CUSTOMER_PHONE] = customerPhone
        params[CFPaymentService.PARAM_CUSTOMER_EMAIL] = customerEmail
        params[CFPaymentService.PARAM_ORDER_CURRENCY] = "INR"
        params[CFPaymentService.PARAM_NOTIFY_URL] = BuildConfig.BASE_URL + "/v1/order/webhook"

        return params
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PaymentFragment()
    }
}