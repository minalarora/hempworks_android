package com.hemp.works.dashboard.prescription.ui

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.DashboardActivity
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorViewModel
import com.hemp.works.databinding.FragmentDosageCalculatorBinding
import com.hemp.works.databinding.FragmentPrescriptionBinding
import com.hemp.works.databinding.FragmentUploadPrescriptionBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.ui.CreateFragment
import com.hemp.works.utils.FileUtils
import com.hemp.works.utils.PreferenceManagerUtil
import javax.inject.Inject


class UploadPrescriptionFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel
    private lateinit var viewModel: UploadPrescriptionViewModel
    private lateinit var binding: FragmentUploadPrescriptionBinding
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.grey_CDCDCD);

        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentUploadPrescriptionBinding>(
            inflater, R.layout.fragment_upload_prescription, container, false).apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@UploadPrescriptionFragment
        }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        binding.prescriptionTitle.setOnClickListener {
            chooseImage()
        }
        
        binding.uploadImageContainer.setOnClickListener {
            if (viewModel.isFile.value == false) {
                chooseImage()
            }
        }

        binding.uploadImageview.setOnClickListener {
            if (viewModel.isFile.value == true) {
                viewModel.removeMedia()
            }
        }
        binding.submit.setOnClickListener { viewModel.createPrescription(
           binding.description.text.toString()
        ) }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.mediaUrl.observe(viewLifecycleOwner) {
            if (!it.success) viewModel.removeMedia()
        }

        viewModel.booleanResponse.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.root.findNavController().popBackStack()
            } else {
                showSnackBar(getString(R.string.something_went_wrong))
            }
        }

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
            arrayOf(FileUtils.MIME_TYPES, FileUtils.MIME_TYPE_PDF)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val chooser: Intent? = Intent.createChooser(intent, getString(R.string.upload_prescription))
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
                        Environment.DIRECTORY_DOCUMENTS
                    )?.let { file ->
                        FileUtils.getFileTypeFromUri(requireContext(), data.data)?.let {
                            viewModel.saveMedia(file, it)
                        }
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
                !mimeType.contains("image") && !mimeType.contains("pdf") -> {
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

    private fun showSnackBar(msg: String) {
        if (viewModel.isFile.value == true) {
            viewModel.removeMedia()
        }
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            UploadPrescriptionFragment()

        private const val PICK_FILE_REQUEST = 200
        private const val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 201
    }
}