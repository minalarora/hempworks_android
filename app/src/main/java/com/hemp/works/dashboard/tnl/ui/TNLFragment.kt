package com.hemp.works.dashboard.tnl.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.info.InfoBottomSheetFragment
import com.hemp.works.dashboard.info.InfoDialogFragment
import com.hemp.works.dashboard.model.LiveSession
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.model.Tutorial
import com.hemp.works.dashboard.prescription.ui.PrescriptionItemClickListener
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.dashboard.prescription.ui.adapters.PrescriptionAdapter
import com.hemp.works.dashboard.tnl.ui.adapters.TNLAdapter
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.databinding.FragmentTnlBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class TNLFragment : Fragment(), Injectable,
    androidx.appcompat.widget.SearchView.OnQueryTextListener, TNLItemClickListener,
    androidx.appcompat.widget.SearchView.OnCloseListener  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: TNLViewModel
    private lateinit var binding: FragmentTnlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentTnlBinding>(
            inflater, R.layout.fragment_tnl, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@TNLFragment
        }

        viewModel.updateFragmentType(TNLType.getTNLTypeFromString(TNLFragmentArgs.fromBundle(requireArguments()).type))

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.recyclerview.adapter = TNLAdapter(this, viewModel.fragmentType)

        binding.search.setOnQueryTextListener(this)
        binding.search.setOnCloseListener(this)


        viewModel.tutorialList.observe(viewLifecycleOwner) {
            if (viewModel.fragmentType == TNLType.TUTORIAL) {
                (binding.recyclerview.adapter as TNLAdapter).submitList(it)
                viewModel.handleListVisibility(it.isEmpty())
            }
        }

        viewModel.newsLetterList.observe(viewLifecycleOwner) {
            if (viewModel.fragmentType == TNLType.NEWSLETTER) {
                (binding.recyclerview.adapter as TNLAdapter).submitList(it)
                viewModel.handleListVisibility(it.isEmpty())
            }
        }

        viewModel.liveSessionList.observe(viewLifecycleOwner) {
            if (viewModel.fragmentType == TNLType.LIVESESSION) {
                (binding.recyclerview.adapter as TNLAdapter).submitList(it)
                viewModel.handleListVisibility(it.isEmpty())
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        when(viewModel.fragmentType) {
            TNLType.TUTORIAL -> viewModel.fetchTutorialList()
            TNLType.NEWSLETTER -> viewModel.fetchNewsLetterList()
            TNLType.LIVESESSION -> viewModel.fetchLiveSessionList()
        }
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        when(viewModel.fragmentType) {
            TNLType.TUTORIAL -> viewModel.updateSearchTextForTutorial(newText)
            TNLType.NEWSLETTER -> viewModel.updateSearchTextForNewsLetter(newText)
            TNLType.LIVESESSION -> viewModel.updateSearchTextForLiveSession(newText)
        }
        return false
    }


    override fun onClose(): Boolean {
        viewModel.clearSearchText()
        return false
    }

    override fun onTutorialClick(tutorial: Tutorial) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(tutorial.url)
            )
        )
    }

    override fun onTutorialReadMore(tutorial: Tutorial) {
        InfoBottomSheetFragment.newInstance(tutorial.description.toString())
            .show(childFragmentManager, InfoBottomSheetFragment.javaClass.simpleName)
    }

    override fun onLiveSessionClick(liveSession: LiveSession) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(liveSession.url)
            )
        )
    }

    override fun onNewsLetterClick(newsLetter: NewsLetter) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(newsLetter.pdf)
            )
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TNLFragment()
    }
}

interface TNLItemClickListener{
    fun onTutorialClick(tutorial: Tutorial)

    fun onTutorialReadMore(tutorial: Tutorial)

    fun onLiveSessionClick(liveSession: LiveSession)

    fun onNewsLetterClick(newsLetter: NewsLetter)
}