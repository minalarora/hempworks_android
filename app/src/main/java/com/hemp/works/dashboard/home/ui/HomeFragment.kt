package com.hemp.works.dashboard.home.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.UserType
import com.hemp.works.dashboard.address.ui.AddressFragmentDirections
import com.hemp.works.dashboard.credit.ui.PendingCreditBottomSheet
import com.hemp.works.dashboard.home.ui.adapters.*
import com.hemp.works.dashboard.model.*
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.dashboard.profile.ui.EditMobileBottomSheetFragment
import com.hemp.works.dashboard.search.ui.SearchFragmentDirections
import com.hemp.works.dashboard.search.ui.SearchItemClickListener
import com.hemp.works.dashboard.tnl.ui.TNLType
import com.hemp.works.databinding.FragmentHomeBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginActivity
import com.hemp.works.login.ui.LoginFragmentDirections
import com.hemp.works.utils.PreferenceManagerUtil
import java.text.SimpleDateFormat
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

        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            selectBottomItem(it)
            true
        }

        ActionBarDrawerToggle(requireActivity(), binding.drawer, binding.toolbar, R.string.open, R.string.close).let {
            binding.drawer.addDrawerListener(it)
            it.isDrawerIndicatorEnabled = true
            it.drawerArrowDrawable.color = ContextCompat.getColor(requireContext(), R.color.white)
            it.syncState()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
//                R.id.search -> {
//                    HomeFragmentDirections.actionHomeFragmentToSearchFragment().also {
//                        binding.root.findNavController().navigate(it)
//                    }
//                }
                R.id.notification -> {
                    HomeFragmentDirections.actionHomeFragmentToNotificationFragment().also {
                        binding.root.findNavController().navigate(it)
                    }
                }
                R.id.cart -> {
                    HomeFragmentDirections.actionHomeFragmentToCartFragment().also {
                        binding.root.findNavController().navigate(it)
                    }
                }

            }
           true
        }

        binding.search.setOnClickListener {
            HomeFragmentDirections.actionHomeFragmentToSearchFragment().also {
                        binding.root.findNavController().navigate(it)
                    }
        }


        binding.bannerRecyclerview.adapter = BannerAdapter(listener = object :
            BannerItemClickListener {
            override fun onItemClick(banner: Banner) {
                if (banner.url == "https://image.techhempworks.co.in/piyush-juneja.jpg") {
                    navigateToCourse()
                }
            }
            }
        )
        ViewCompat.setNestedScrollingEnabled(binding.bannerRecyclerview, false);

        binding.categoryRecyclerview.adapter = CategoryAdapter(listener = object :
            CategoryItemClickListener {
            override fun onItemClick(categoryid: Long) {
                HomeFragmentDirections.
                actionHomeFragmentToProductListFragment(null, categoryid.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.categoryRecyclerview, false)

        binding.trendingProductRecyclerview.adapter = ProductAdapter(object :
            ProductItemClickListener {
            override fun onProductClick(product: Product) {
                HomeFragmentDirections.actionHomeFragmentToProductFragment(product.id.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })

        ViewCompat.setNestedScrollingEnabled(binding.trendingProductRecyclerview, false)

        binding.allProductRecyclerview.adapter = ProductAdapter(object :
            ProductItemClickListener {
            override fun onProductClick(product: Product) {
                HomeFragmentDirections.actionHomeFragmentToProductFragment(product.id.toString()).let {
                    binding.root.findNavController().navigate(it)
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.allProductRecyclerview, false)

        binding.instagramRecyclerview.adapter = InstagramAdapter(object :
            InstagramItemClickListener {
            override fun onItemClick(instagram: Instagram) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(instagram.post)
                    )
                )
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.instagramRecyclerview, false)

        binding.blogRecyclerview.adapter = BlogAdapter(object :
            BlogItemClickListener {
            override fun onItemClick(blog: Blog) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(blog.url)
                    )
                )
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.blogRecyclerview, false)

        binding.uploadPrescription.setOnClickListener {
            HomeFragmentDirections.actionHomeFragmentToPrescriptionFragment().also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.dosageCalculator.setOnClickListener {
            HomeFragmentDirections.actionHomeFragmentToDosageCalculatorFragment().also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.offerBanner.setOnClickListener {
            HomeFragmentDirections.actionHomeFragmentToOfferFragment().also {
                binding.root.findNavController().navigate(it)
            }
        }

        binding.disclaimer.text = HtmlCompat.fromHtml("<b>DISCLAIMER</b><br>" + requireContext().getString(R.string.disclaimer_home), HtmlCompat.FROM_HTML_MODE_LEGACY)

        viewModel.bannerList.observe(viewLifecycleOwner) {
            (binding.bannerRecyclerview.adapter as BannerAdapter).submitList(it)
            viewModel.handleBannerVisibility(it.isEmpty())
            if (it.isNotEmpty()) viewModel.startScrolling()
        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            (binding.categoryRecyclerview.adapter as CategoryAdapter).submitList(it)
            viewModel.handleCategoryVisibility(it.isEmpty())
        }

        viewModel.bestSellerProductList.observe(viewLifecycleOwner) {
            (binding.trendingProductRecyclerview.adapter as ProductAdapter).submitList(it)
            viewModel.handleBSProductVisibility(it.isEmpty())
        }

        viewModel.allProductList.observe(viewLifecycleOwner) {
            (binding.allProductRecyclerview.adapter as ProductAdapter).submitList(it)
            viewModel.handleAllProductVisibility(it.isEmpty())
        }

        viewModel.instagramList.observe(viewLifecycleOwner) {
            (binding.instagramRecyclerview.adapter as InstagramAdapter).submitList(it)
            viewModel.handleInstagramVisibility(it.isEmpty())
        }

        viewModel.blogList.observe(viewLifecycleOwner) {
            (binding.blogRecyclerview.adapter as BlogAdapter).submitList(it)
            viewModel.handleBlogVisibility(it.isEmpty())
        }

        viewModel.scroll.observe(viewLifecycleOwner) {
            binding.bannerRecyclerview.smoothScrollToPosition(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            PreferenceManagerUtil.clear(requireContext())
            LoginActivity.getPendingIntent(requireContext(), R.id.loginFragment).send()
            requireActivity().finish()
        }

        viewModel.pendingAmount.observe(viewLifecycleOwner) {
            showPendingAmountBottomSheet(it)
        }

        if (sharedViewModel.isFirstTime && sharedViewModel.userType == UserType.APPROVED) {
            viewModel.getPendingAmount()
            sharedViewModel.isFirstTime = false
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.home
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
            R.id.log_out -> {
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
            R.id.create_account -> {
                PreferenceManagerUtil.clear(requireContext())
                LoginActivity.getPendingIntent(requireContext(), R.id.loginFragment).send()
                requireActivity().finish()
            }
            R.id.profile -> {
                HomeFragmentDirections.actionHomeFragmentToProfileFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.upload_prescription -> {
                HomeFragmentDirections.actionHomeFragmentToPrescriptionFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.dosage_calculator -> {
                HomeFragmentDirections.actionHomeFragmentToDosageCalculatorFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.newsletter -> {
                HomeFragmentDirections.actionHomeFragmentToTNLFragment(TNLType.NEWSLETTER.type).also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.live_sessions -> {
                HomeFragmentDirections.actionHomeFragmentToTNLFragment(TNLType.LIVESESSION.type).also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.tutorials -> {
                HomeFragmentDirections.actionHomeFragmentToTNLFragment(TNLType.TUTORIAL.type).also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.support -> {
                HomeFragmentDirections.actionHomeFragmentToSupportFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.orders -> {
                HomeFragmentDirections.actionHomeFragmentToOrderFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.privacy -> {
                HomeFragmentDirections.actionHomeFragmentToPrivacyFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.tac -> {
                HomeFragmentDirections.actionHomeFragmentToTACFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.courses -> {
                navigateToCourse()
            }
            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }

        binding.drawer.closeDrawers()
    }

    private fun selectBottomItem(menuItem: MenuItem) {

        when(menuItem.itemId) {
            R.id.home -> {

            }
            R.id.products -> {
                HomeFragmentDirections.actionHomeFragmentToAllProductListFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.support -> {
                HomeFragmentDirections.actionHomeFragmentToAllSupportFragment().also {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.account -> {
                if (sharedViewModel.userType == UserType.ANONYMOUS) {
                    navigateToLogin()
                } else {
                    HomeFragmentDirections.actionHomeFragmentToAccountFragment().also {
                        binding.root.findNavController().navigate(it)
                    }
                }
            }


            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }
    }


    private fun showPendingAmountBottomSheet(response: ResponsePendingAmount) {

        if (response.pendingamount == null ||
                response.pendingamount == 0) {
            return
        }
        val dateFormat = SimpleDateFormat(Constants.ONLY_DATE_FORMAT);
        val date = dateFormat.format(response.date!!)

        PendingCreditBottomSheet.newInstance(response.pendingamount.toString(), date, "HOME")
            .show(requireActivity().supportFragmentManager, PendingCreditBottomSheet.javaClass.simpleName)

        requireActivity().supportFragmentManager.setFragmentResultListener(getString(R.string.pending_amount_title), this, object :
            FragmentResultListener {
            override fun onFragmentResult(requestKey: String, bundle: Bundle) {
                val amount  = bundle.getString("amount", "")
                HomeFragmentDirections.actionHomeFragmentToPaymentFragment(
                    RequestPayment(
                        payment = "DIRECT",
                        discountprice = amount.toInt()!!,
                        amount = amount.toInt()!!,
                        reason = "CREDIT")
                ).also {
                    binding.root.findNavController().navigate(it)
                }
            } })
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

    private fun navigateToCourse() {
        if (sharedViewModel.user?.course == "enable") {
            HomeFragmentDirections.actionHomeFragmentToCourseFragment().also {
                binding.root.findNavController().navigate(it)
            }
        } else if (sharedViewModel.user?.course == "disable") {
            HomeFragmentDirections.actionHomeFragmentToCourseTACFragment().also {
                binding.root.findNavController().navigate(it)
            }
        } else {
            showSnackBar(getString(R.string.course_snackbar))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}

interface CategoryItemClickListener{
    fun onItemClick(categoryid: Long)
}

interface BlogItemClickListener{
    fun onItemClick(blog: Blog)
}

interface InstagramItemClickListener{
    fun onItemClick(model: Instagram)
}