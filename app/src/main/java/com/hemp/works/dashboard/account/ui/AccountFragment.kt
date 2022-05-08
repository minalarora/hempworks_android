package com.hemp.works.dashboard.account.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.account.ui.adapters.AccountAdapter
import com.hemp.works.dashboard.home.ui.AllProductListFragmentDirections
import com.hemp.works.dashboard.home.ui.HomeFragmentDirections
import com.hemp.works.dashboard.home.ui.HomeViewModel
import com.hemp.works.dashboard.home.ui.adapters.BannerAdapter
import com.hemp.works.databinding.FragmentAccountBinding
import com.hemp.works.databinding.FragmentHomeBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginActivity
import com.hemp.works.login.data.model.Address
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject


class AccountFragment : Fragment(), Injectable, AccountItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentAccountBinding>(
            inflater, R.layout.fragment_account, container, false).apply {
            this.viewmodel = viewModel
            this.usertype = sharedViewModel.userType
            this.user = sharedViewModel.user
            lifecycleOwner = this@AccountFragment
        }

        binding.profile.let {
            Glide.with(requireContext())
                .load(sharedViewModel.user?.profile)
                .placeholder(R.drawable.doctor_placeholder)
                .error(R.drawable.doctor_placeholder)
                .apply(MyAppGlideModule.requestOptions)
                .into(it);
        }

        binding.recyclerview.adapter = AccountAdapter(this).also {
            it.submitList(listOf(*requireContext().resources.getStringArray(R.array.account_list)))
        }



        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            selectBottomItem(it)
            true
        }


        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }


        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            navigateToLogin()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.account
    }

    private fun selectBottomItem(menuItem: MenuItem) {

        when(menuItem.itemId) {
            R.id.home -> {
                AccountFragmentDirections.actionAccountFragmentToHomeFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.products -> {
                AccountFragmentDirections.actionAccountFragmentToAllProductListFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.support -> {
                AccountFragmentDirections.actionAccountFragmentToAllSupportFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.account -> {

            }

        }
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToLogin() {
        PreferenceManagerUtil.clear(requireContext())
        LoginActivity.getPendingIntent(requireContext(), R.id.loginFragment).send()
        requireActivity().finish()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            AccountFragment()
    }

    override fun onItemClick(string: String) {
        when(string) {
            getString(R.string.account_profile) -> {
                AccountFragmentDirections.actionAccountFragmentToProfileFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            getString(R.string.account_orders) -> {
                AccountFragmentDirections.actionAccountFragmentToOrderFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            getString(R.string.account_payment) -> {
                AccountFragmentDirections.actionAccountFragmentToPaymentHistoryFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            getString(R.string.account_prescriptions) -> {
                AccountFragmentDirections.actionAccountFragmentToPrescriptionFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            getString(R.string.account_credit) -> {
                AccountFragmentDirections.actionAccountFragmentToWalletFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            getString(R.string.account_ledger) -> {

            }
            getString(R.string.account_logout) -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.log_out))
                    .setMessage(resources.getString(R.string.confirm_logout_account))
                    .setNegativeButton(getString(R.string.no)) { dialog, which ->

                    }
                    .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.logout()
                    }
                    .show()
            }
        }
    }
}

interface AccountItemClickListener {

    fun onItemClick(string: String)

}