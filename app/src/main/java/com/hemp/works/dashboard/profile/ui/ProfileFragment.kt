package com.hemp.works.dashboard.profile.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.databinding.FragmentProfileBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.ui.CreateFragment
import com.hemp.works.utils.FileUtils
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject


class ProfileFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_F8AA37);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false).apply {
            this.viewmodel = viewModel
            this.usertype = sharedViewModel.userType
            lifecycleOwner = this@ProfileFragment
        }

        binding.addProfile.setOnClickListener { chooseImage() }
        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }
        binding.emailEdit.setOnClickListener {  EditEmailBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, EditEmailBottomSheetFragment.javaClass.simpleName) }
        binding.mobileEdit.setOnClickListener {  EditMobileBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, EditMobileBottomSheetFragment.javaClass.simpleName) }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (it) viewModel.fetchDoctor() else showSnackBar(Constants.GENERAL_ERROR_MESSAGE)
        }

        viewModel.doctor.observe(viewLifecycleOwner) {
            sharedViewModel.user = it
            PreferenceManagerUtil.putDoctor(requireContext(), it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(getString(R.string.update_email), this, object :
            FragmentResultListener {
            override fun onFragmentResult(requestKey: String, bundle: Bundle) {
                    viewModel.updateEmail(bundle.getString("email", ""))
            } })

        requireActivity().supportFragmentManager.setFragmentResultListener(getString(R.string.update_mobile_number), this, object :
            FragmentResultListener {
            override fun onFragmentResult(requestKey: String, bundle: Bundle) {
                viewModel.updateMobile(bundle.getString("mobile", ""), bundle.getString("otp", ""))
            } })

        requireActivity().supportFragmentManager.setFragmentResultListener(getString(R.string.enter_otp), this, object :
            FragmentResultListener {
            override fun onFragmentResult(requestKey: String, bundle: Bundle) {
                viewModel.sendOTP(bundle.getString("mobile", ""))
            } })

        return binding.root

    }

    private fun chooseImage() {
        if (FileUtils.checkPermission(permissions, requireActivity())) {
            openFileChooserDialog()
        } else {
            requestPermissions()
        }
    }


    private fun requestPermissions() {
        requestPermissions(permissions, REQUEST_PERMISSIONS_CODE_WRITE_STORAGE)
    }

    private fun showSnackBar(msg: String) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE_WRITE_STORAGE) {
            var granted = true
            for (result in grantResults) {
                granted = result == PackageManager.PERMISSION_GRANTED
                if (!granted) break
            }
            if (granted) {
                openFileChooserDialog()
            } else {
                showSnackBar(getString(R.string.permissions_required))
            }
        }
    }

    private fun openFileChooserDialog() {
        val mimeTypes =
            arrayOf(FileUtils.MIME_TYPES)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val chooser: Intent? = Intent.createChooser(intent, getString(R.string.select_doctor_certificate))
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        this.startActivityForResult(
            chooser,
            PICK_FILE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK && requestCode == PICK_FILE_REQUEST) {
                if (isFileUploadAllowed(data.data, FileUtils.MAX_FILE_SIZE)) {
                    // Get the Uri of the selected file
                    FileUtils.getFileFromUri(
                        requireContext(),
                        data.data,
                        getString(R.string.doctor_certificate),
                        Environment.DIRECTORY_PICTURES
                    )?.let {
                        viewModel.updateImage(it)
                    }
                }
            }
        }
    }

    private fun isFileUploadAllowed(uri: Uri?, maxFileSize: Int): Boolean {
        val mimeType: String? = uri?.let { requireContext().contentResolver.getType(it) }
        if (mimeType != null) {
            return when {
                FileUtils.getFileSize(requireContext(), uri) > maxFileSize -> {
                    showSnackBar(requireContext().getString(R.string.uploaded_file_exceeded_size_limit))
                    false
                }
                FileUtils.getFileSize(requireContext(), uri) == 0L -> {
                    showSnackBar(requireContext().getString(R.string.please_select_valid_file))
                    false
                }
                !mimeType.contains("image") -> {
                    showSnackBar(requireContext().getString(R.string.please_select_valid_file))
                    false
                }
                else -> {
                    true
                }
            }
        }
        return false
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment()

        private const val PICK_FILE_REQUEST = 100
        private const val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 101
    }
}