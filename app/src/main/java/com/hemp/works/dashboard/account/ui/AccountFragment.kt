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
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.account.ui.adapters.AccountAdapter
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
            PreferenceManagerUtil.clear(requireContext())
            LoginActivity.getPendingIntent(requireContext(), R.id.loginFragment).send()
            requireActivity().finish()
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

            }
            R.id.products -> {

            }
            R.id.support -> {

            }
            R.id.account -> {

            }


            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AccountFragment()
    }

    override fun onItemClick(string: String) {

    }
}

interface AccountItemClickListener {

    fun onItemClick(string: String)

}