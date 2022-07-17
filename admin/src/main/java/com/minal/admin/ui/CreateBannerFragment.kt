package com.minal.admin.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.R
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestCreateBanner
import com.minal.admin.data.request.RequestOrderList
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentCreateBannerBinding
import com.minal.admin.databinding.FragmentCreateCouponBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.FileUtils
import java.io.File

class CreateBannerFragment : BaseFragment<FragmentCreateBannerBinding>() {

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val mRequestCreateBanner by lazy { RequestCreateBanner() }

    private lateinit var viewModel : AdminViewModel
    var token:String?=null
    var imageFile: File?=null


    private  val PICK_FILE_REQUEST = 100
    private  val REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 101

    companion object {
        val TAG: String = CreateBannerFragment::class.java.simpleName
        fun getInstance() = CreateBannerFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentCreateBannerBinding {
        return FragmentCreateBannerBinding.inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi(){

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        token?.let {
            viewModel?.getProductAll(it)
        }

        viewModel?.loading?.observe(viewLifecycleOwner){
            if (it){
                baseActivity.showProgress()
            }
            else
                baseActivity.hideProgress()
        }

        viewModel?.uploadImage?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{

                    mRequestCreateBanner.apply {
                        url = it.data.url
                    }
                    viewModel?.createBanner(token,mRequestCreateBanner)

                }
                is Result.Error ->
                {

                }
            }
        }

        viewModel?.createBanner?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{
                    baseActivity.showToast("You have successfully created banner.")

                    baseActivity.onBackPressed()


                }
                is Result.Error ->
                {
                }

            }
        }

    }

    private fun setListener(){

        mBinding.idBtnAdd.setOnClickListener {
            chooseImage()
        }

        mBinding.idBtnBanner.setOnClickListener {
            if (imageFile != null)
            token?.let { it1 -> imageFile?.let { it2 -> viewModel.uploadImage(it1, it2) } }
            else
                baseActivity.showToast("Please pick image.")
        }
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

    private fun isFileUploadAllowed(uri: Uri?, maxFileSize: Int): Boolean {
        val mimeType: String? = uri?.let { requireContext().contentResolver.getType(it) }
        if (mimeType != null) {
            return when {
                FileUtils.getFileSize(requireContext(), uri) > maxFileSize -> {
                    baseActivity.showToast("The uploaded file is above the 10 MB size limit.")
                    false
                }
                FileUtils.getFileSize(requireContext(), uri) == 0L -> {
                    baseActivity.showToast("Please select a valid file.")

                    false
                }
                !mimeType.contains("image") -> {
                    baseActivity.showToast("Please select a valid file.")

                    false
                }
                else -> {
                    true
                }
            }
        }
        return false
    }


    private fun openFileChooserDialog() {
        val mimeTypes =
            arrayOf(FileUtils.MIME_TYPES)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val chooser: Intent? = Intent.createChooser(intent, getString(R.string.select_picture))
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
                        getString(R.string.banner_picture),
                        Environment.DIRECTORY_PICTURES
                    )?.let {
                        imageFile = it
                        mBinding.idTvIgName.text = it.toString()
                    }
                }
            }
        }
    }

}