package com.hemp.works.dashboard.course.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.databinding.FragmentCourseBinding
import com.hemp.works.databinding.FragmentCourseTACBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import javax.inject.Inject


class CourseTACFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: CourseViewModel
    private lateinit var binding: FragmentCourseTACBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCourseTACBinding>(
            inflater, R.layout.fragment_course_t_a_c, container, false).apply {
            this.viewmodel = viewmodel
            lifecycleOwner = this@CourseTACFragment
        }

        binding.acceptNow.setOnClickListener { applyForCourse() }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (it) {
                sharedViewModel.user?.apply {
                    course = "apply"
                }
                Toast.makeText(requireContext(), getString(R.string.thank_you_for_apply), Toast.LENGTH_LONG).show()
                binding.root.findNavController().popBackStack()
            } else {
                showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
            }
        }

        binding.text.text =  HtmlCompat.fromHtml( requireContext().getString(R.string.course_tac_desc), HtmlCompat.FROM_HTML_MODE_LEGACY)

        return binding.root

    }

    private fun applyForCourse() {
        if (binding.agree.isChecked) {
            viewModel.applyForCourses()
        } else {
            showSnackBar(getString(R.string.course_tac_checkbox_error))
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
            CourseTACFragment()
    }
}