package com.minal.admin.ui

import android.R
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentPrescripBinding
import com.minal.admin.databinding.FragmentProductUpdateBinding

class ProductUpdateFragment: BaseFragment<FragmentProductUpdateBinding>() {

    private lateinit var viewModel : AdminViewModel
    var token:String?=null
    var itemFirst:ArrayList<ResponseProduct>?=null
    var proId:Long?=null
    var varId:Long?=null
    var variantListFirst: List<ResponseProduct.Variant?>?=null
    var InStock:Boolean?=null
    var Active:Boolean?=null

    companion object {
        val TAG: String = ProductUpdateFragment::class.java.simpleName
        fun getInstance() = ProductUpdateFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentProductUpdateBinding {
        return FragmentProductUpdateBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token  = PreferenceManager.getDefaultSharedPreferences(context)?.
        getString(RestConstant.AUTH_TOKEN, "").toString()

        token?.let {
            viewModel?.getProductAll(it)
        }


        viewModel?.homeCategory?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{
                    itemFirst = it.data



                    val Category = arrayOfNulls<String>(it.data.size!!)

                    for (i in 0 until it.data.size!!) {
                        Category[i]= it.data.get(i)?.title.toString()

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            Category
                        )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

                        mBinding.idEdtPro.setAdapter(spinnerArrayAdapter)

                    }
                }
                is Result.Error ->
                {
                }

            }
        }

    }

    private fun setListener() {

        mBinding.idEdtPro.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {

                proId = itemFirst?.get(position)?.id

                variantListFirst = itemFirst?.get(position)?.variants

                val Category =  arrayOfNulls<String>(variantListFirst?.size!!)


                for (i in 0 until variantListFirst?.size!!) {

                    Category[i] = "${variantListFirst?.get(i)?.size.toString()} ${variantListFirst?.get(i)?.type.toString()}"
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        Category
                    )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view
                    mBinding.idEdtVar.setAdapter(spinnerArrayAdapter)
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })


        mBinding.idEdtVar.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {

                varId = variantListFirst?.get(position)?.id

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        mBinding.idRbISTrue.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbISTrue.isChecked = true
                mBinding.idRbISFalse.isChecked = false
                InStock = true
            }
        }

        mBinding.idRbISFalse.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbISTrue.isChecked = false
                mBinding.idRbISFalse.isChecked = true
                InStock = false
            }
        }


        mBinding.idRbAtTrue.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbAtTrue.isChecked = true
                mBinding.idRbAtFalse.isChecked = false
                Active = true
            }
        }

        mBinding.idRbAtFalse.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbAtTrue.isChecked = false
                mBinding.idRbAtFalse.isChecked = true
                Active = false
            }
        }

        mBinding.idBtnCreate.setOnClickListener {

        }
    }
}