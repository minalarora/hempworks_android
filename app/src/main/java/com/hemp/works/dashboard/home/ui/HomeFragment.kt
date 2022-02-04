package com.hemp.works.dashboard.home.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.home.ui.adapters.BannerAdapter
import com.hemp.works.dashboard.home.ui.adapters.CategoryAdapter
import com.hemp.works.databinding.FragmentHomeBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginActivity
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                    binding.drawer.closeDrawer(GravityCompat.START)
                } else {
                    requireActivity().finish()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false).apply {
            this.viewmodel = viewModel
            this.usertype = sharedViewModel.userType
            this.user = sharedViewModel.user
            lifecycleOwner = this@HomeFragment
        }

        binding.navigationView.setNavigationItemSelectedListener{
            selectDrawerItem(it)
            false
        }

        ActionBarDrawerToggle(requireActivity(), binding.drawer, binding.toolbar, R.string.open, R.string.close).let {
            binding.drawer.addDrawerListener(it)
            it.isDrawerIndicatorEnabled = true
            it.drawerArrowDrawable.color = ContextCompat.getColor(requireContext(), R.color.white)
            it.syncState()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.search -> {
                    //TODO: SEARCH
                }
                R.id.notification -> {
                    //TODO: NOTIFICATION
                }

            }
           true
        }

        binding.bannerRecyclerview.adapter = BannerAdapter()
        ViewCompat.setNestedScrollingEnabled(binding.bannerRecyclerview, false);

        binding.categoryRecyclerview.adapter = CategoryAdapter()
        ViewCompat.setNestedScrollingEnabled(binding.categoryRecyclerview, false)

        viewModel.bannerList.observe(viewLifecycleOwner) {
            (binding.bannerRecyclerview.adapter as BannerAdapter).submitList(it)
            viewModel.startScrolling()
        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            (binding.categoryRecyclerview.adapter as CategoryAdapter).submitList(it)
        }
        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        when(menuItem.itemId) {
            R.id.log_out, R.id.create_account -> {
                PreferenceManagerUtil.clear(requireContext())
                LoginActivity.getPendingIntent(requireContext(), R.id.loginFragment).send()
                requireActivity().finish()
                //TODO: LOG OUT API CALL
            }
            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }

        binding.drawer.closeDrawers()
    }


    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}

//if we put RecyclerView inside NestedScrollView,
//RecyclerView's smooth scrolling is disturbed. So to bring back smooth scrolling there's trick:
//ViewCompat.setNestedScrollingEnabled(recyclerView, false);