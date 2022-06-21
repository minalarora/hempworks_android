package com.minal.admin.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hoobio.base.BaseFragment
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.RequestCreateCoupon
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentAdminBinding
import com.minal.admin.databinding.FragmentCreateCouponBinding
import com.minal.admin.ext_fun.hide
import com.minal.admin.ext_fun.show
import android.widget.AdapterView
import android.R

import android.widget.ArrayAdapter
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.AuthValidation


class CreateCouponFragment: BaseFragment<FragmentCreateCouponBinding>() {

    private lateinit var viewModel : AdminViewModel
    private val mRequestCreateCoupon by lazy { RequestCreateCoupon() }
    var couponType:Boolean?=null
    var disType:String?=null
    var token:String?=null
    var proId:Long?=null
    var varId:Long?=null
    var addProId:Long?=null
    var addVarId:Long?=null

    var item:ArrayList<ResponseProduct>?=null
    var itemVar:ArrayList<ResponseProduct.Variant>?=null

    var isQuantity:Boolean?=null
    var adapter: ArrayAdapter<String>?=null
    private val mProductAdapter by lazy {
        ProductAdapter()
    }

    companion object {
        val TAG: String = CreateCouponFragment::class.java.simpleName
        fun getInstance() = CreateCouponFragment()
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentCreateCouponBinding {
        return FragmentCreateCouponBinding.inflate(inflater, container, false)
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
                    Log.d("data",it.data.toString())
                    item = it.data
                    itemVar = it.data.getOrNull(0)?.variants as ArrayList<ResponseProduct.Variant>

//                    mProductAdapter.addItems(it.data.getOrNull(0)?.variants)

                    for (f in 0 until it.data.size){
                        val Categorys = arrayOfNulls<String>(it.data.getOrNull(f)?.variants?.size!!)

                        for (i in 0 until it.data.getOrNull(f)?.variants?.size!!) {
                            Categorys[i]= it.data.getOrNull(i)?.variants?.get(i)?.size.toString()

                            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                Categorys
                            )
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view
                            mBinding.idEdtVar.setAdapter(spinnerArrayAdapter)
                            mBinding.idEdtAddVar.setAdapter(spinnerArrayAdapter)
                        }
                    }


                    val Category = arrayOfNulls<String>(it.data.size!!)

                    for (i in 0 until it.data.size!!) {
                        Category[i]= it.data.get(i)?.title.toString()

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            Category
                        )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

                        mBinding.idEdtPro.setAdapter(spinnerArrayAdapter)
                        mBinding.idEdtAddPro.setAdapter(spinnerArrayAdapter)

                    }
                }
                is Result.Error ->
                {
                }

            }
        }


        viewModel?.coupons?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{
                    Log.d("data",it.data.toString())
                    baseActivity.showToast("You have successfully created coupon.")
                    baseActivity.onBackPressed()

                }
                is Result.Error ->
                {
                }
            }
        }
    }


    private fun setListener() {


         mBinding.idBtnCreate.setOnClickListener {
             if (AuthValidation.couponValidation(requireContext(),this.mBinding)){
                 token?.let { it1 -> viewModel.createCoupon(it1,getRequestCoupon()) }
             }
         }


        mBinding.idEdtVar.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {

                varId = itemVar?.get(position)?.id
                Log.d("id",id.toString())
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })


        mBinding.idEdtPro.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {


                 proId = item?.get(position)?.id

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })


        mBinding.idEdtAddVar.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {

                addVarId = itemVar?.get(position)?.id
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })


        mBinding.idEdtAddPro.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {
                addProId = item?.get(position)?.id
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })




        mBinding.idRbPublic.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPublic.isChecked = true
                mBinding.idRbPrivate.isChecked = false
                couponType = true
            }
        }

        mBinding.idRbPrivate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPublic.isChecked = false
                mBinding.idRbPrivate.isChecked = true
                couponType = false
            }
        }

        mBinding.idRbPercentage.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPercentage.isChecked = true
                mBinding.idRbValue.isChecked = false
                mBinding.idRbQuantity.isChecked = false
                mBinding.idRbCredit.isChecked = false
                disType = "percentage"
                isQuantity = false
                mBinding.idRlQuantity.hide()
//                mBinding.idEdtPercentage.hint = getString(com.minal.admin.R.string.percentage)
            }
        }

        mBinding.idRbValue.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPercentage.isChecked = false
                mBinding.idRbValue.isChecked = true
                mBinding.idRbQuantity.isChecked = false
                mBinding.idRbCredit.isChecked = false
                disType = "value"
                isQuantity = false

                mBinding.idRlQuantity.hide()

            }
        }


        mBinding.idRbQuantity.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPercentage.isChecked = false
                mBinding.idRbCredit.isChecked = false
                mBinding.idRbValue.isChecked = false
                mBinding.idRbQuantity.isChecked = true

                disType = "quantity"
                isQuantity = true

                mBinding.idRlQuantity.show()

            }
        }


        mBinding.idRbCredit.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.idRbPercentage.isChecked = false
                mBinding.idRbValue.isChecked = false
                mBinding.idRbQuantity.isChecked = false
                mBinding.idRbCredit.isChecked = true
                disType = "credit"
                isQuantity = false

                mBinding.idRlQuantity.hide()

            }
        }

    }

     private fun getRequestCoupon(): RequestCreateCoupon {
        return mRequestCreateCoupon.apply {
            name  = mBinding.idEdtName.text.toString()
            description = mBinding.idEdtDis.text.toString()
            canuse = 3
            public = couponType
            type = disType
            value = mBinding.idEdtValue.text.toString()
            if (isQuantity == true){
                product = proId
                addproduct = addProId
                variant = varId
                addvariant = addVarId
            }


        }
    }



}