package com.hemp.works.dashboard.support.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.UserType
import com.hemp.works.dashboard.model.Message
import com.hemp.works.dashboard.support.ui.adapters.SupportAdapter
import com.hemp.works.databinding.FragmentSupportBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.utils.PreferenceManagerUtil
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class SupportFragment : Fragment(), Injectable, SupportItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: SupportViewModel
    private lateinit var binding: FragmentSupportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        if (sharedViewModel.userType == UserType.ANONYMOUS) {
            var uniqueId = PreferenceManagerUtil.getString(requireContext(), Constants.UNIQUE_ID)
            if (uniqueId.isNullOrBlank()) {
                uniqueId = UUID.randomUUID().toString()
                PreferenceManagerUtil.putString(requireContext(), Constants.UNIQUE_ID, uniqueId)
            }
            viewModel.initChat(uniqueId)

        } else {
            PreferenceManagerUtil.putString(requireContext(), Constants.UNIQUE_ID, "")
            viewModel.initChat(sharedViewModel.user?.id!!)
        }

        binding = DataBindingUtil.inflate<FragmentSupportBinding>(
            inflater, R.layout.fragment_support, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner =this@SupportFragment
        }

        binding.back.setOnClickListener{
            binding.root.findNavController().popBackStack()
        }

        binding.sent.setOnClickListener{
            viewModel.sendMessage(binding.message.text?.toString())
            binding.message.text = null
        }

        binding.supportRecyclerview.adapter = SupportAdapter(this)

        viewModel.chat.observe(viewLifecycleOwner) {
            (binding.supportRecyclerview.adapter as SupportAdapter).submitList(ArrayList(it.messages))
            if((binding.supportRecyclerview.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() > (binding.supportRecyclerview.adapter as SupportAdapter).itemCount - 3) {
                binding.supportRecyclerview.scrollToPosition((binding.supportRecyclerview.adapter as SupportAdapter).itemCount - 1)
            }
        }



        viewModel.error.observe(viewLifecycleOwner) {
            if (it !=  Constants.CHAT_ERROR) showSnackBar(it)
        }


        return binding.root
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SupportFragment()
    }

    override fun onRetry(message: Message) {
        viewModel.retryMessage(message)
    }
}

interface SupportItemClickListener{
    fun onRetry(message: Message)
}