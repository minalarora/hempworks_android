package com.hemp.works.dashboard.course.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.course.ui.adapter.CourseAdapter
import com.hemp.works.dashboard.model.Course
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.tnl.ui.TNLFragmentArgs
import com.hemp.works.dashboard.tnl.ui.TNLItemClickListener
import com.hemp.works.dashboard.tnl.ui.TNLType
import com.hemp.works.dashboard.tnl.ui.TNLViewModel
import com.hemp.works.dashboard.tnl.ui.adapters.TNLAdapter
import com.hemp.works.databinding.FragmentCourseBinding
import com.hemp.works.databinding.FragmentTnlBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.utils.FileUtils
import javax.inject.Inject


class CourseFragment : Fragment(), Injectable,
    androidx.appcompat.widget.SearchView.OnQueryTextListener, CourseItemClickListener,
    androidx.appcompat.widget.SearchView.OnCloseListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: CourseViewModel
    private lateinit var binding: FragmentCourseBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)


        binding = DataBindingUtil.inflate<FragmentCourseBinding>(
            inflater, R.layout.fragment_course, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@CourseFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }


        binding.recyclerview.adapter = CourseAdapter(this)

        binding.search.setOnQueryTextListener(this)
        binding.search.setOnCloseListener(this)

        viewModel.courseList.observe(viewLifecycleOwner) {
                (binding.recyclerview.adapter as CourseAdapter).submitList(it)
                viewModel.handleListVisibility(it.isEmpty())
        }


        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        return binding.root
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
        viewModel.updateSearchTextForCourses(newText)
        return false
    }


    override fun onClose(): Boolean {
        viewModel.clearSearchText()
        return false
    }

    override fun onItemClick(newsLetter: Course) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW
                ).apply { setDataAndType( Uri.parse(newsLetter.pdf), FileUtils.MIME_TYPE_PDF)}
            )
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), Constants.GENERAL_ERROR_MESSAGE , Toast.LENGTH_SHORT).show()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CourseFragment()
    }
}

interface CourseItemClickListener {
    fun onItemClick(course: Course)
}