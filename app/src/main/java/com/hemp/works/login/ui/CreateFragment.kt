package com.hemp.works.login.ui
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
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
import com.hemp.works.dashboard.DashboardActivity
import com.hemp.works.databinding.FragmentCreateBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.CreateViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import com.hemp.works.utils.FileUtils
import com.hemp.works.utils.FileUtils.MIME_TYPES
import com.hemp.works.utils.PreferenceManagerUtil
import com.onesignal.OneSignal
import java.io.File
import javax.inject.Inject


class CreateFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: CreateViewModel
    private lateinit var binding: FragmentCreateBinding
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCreateBinding>(
            inflater, R.layout.fragment_create, container, false).apply {
            this.viewmodel = this@CreateFragment.viewModel
            lifecycleOwner = this@CreateFragment
        }

        viewModel.mobile = CreateFragmentArgs.fromBundle(requireArguments()).mobile!!

        binding.uploadImageContainer.setOnClickListener {
            if (viewModel.isFile.value == false) {
                chooseImage()
            }
        }

        binding.uploadImageview.setOnClickListener {
            if (viewModel.isFile.value == true) {
                viewModel.removeImage()
            }
        }

        binding.submit.setOnClickListener { viewModel.createDoctor(
            name = binding.name.text.toString(),
            clinic = binding.clinic.text.toString(),
            email = binding.email.text.toString(),
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString(),
            addressLine1 = binding.addressLine1.text.toString(),
            addressLine2 = binding.addressLine2.text.toString(),
            city = binding.city.text.toString(),
            state = binding.state.selectedItem.toString(),
            pincode = binding.pincode.text.toString(),
            notification = OneSignal.getDeviceState()?.userId
        ) }

        binding.back.setOnClickListener { binding.root.findNavController().popBackStack() }

        viewModel.imageUrl.observe(viewLifecycleOwner) {
            if (!it.success) viewModel.removeImage()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.doctor.observe(viewLifecycleOwner) {
            if (it.doctor == null) {
                showSnackBar(getString(R.string.something_went_wrong))
            } else {
                PreferenceManagerUtil.putString(requireContext(), Constants.USER_TYPE, Constants.DOCTOR)
                PreferenceManagerUtil.putString(requireContext(), Constants.AUTH_TOKEN, it.token)
                PreferenceManagerUtil.putDoctor(requireContext(), it.doctor!!)
                Intent(requireActivity(), DashboardActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }
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
            arrayOf(MIME_TYPES)
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
                       viewModel.saveImage(it)
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

    private fun showSnackBar(msg: String) {
        if (viewModel.isFile.value == true) {
            viewModel.removeImage()
        }
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateFragment()

        private const val PICK_FILE_REQUEST = 100
        private const val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 101
    }
}