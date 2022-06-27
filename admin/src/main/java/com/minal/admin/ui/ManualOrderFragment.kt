package com.minal.admin.ui

import android.R
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoobio.base.BaseFragment
import com.minal.admin.constant.BundleConstant
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.data.remote.Result
import com.minal.admin.data.request.ReqAddManualOrder
import com.minal.admin.data.response.ProductList
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.data.viewmodel.AdminViewModel
import com.minal.admin.databinding.FragmentManualOrderBinding
import com.minal.admin.ext_fun.baseActivity
import com.minal.admin.ext_fun.showToast
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.view.TextViewDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ManualOrderFragment: BaseFragment<FragmentManualOrderBinding>() {

    private val mReqAddManualOrder by lazy { ReqAddManualOrder() }

    private lateinit var viewModel: AdminViewModel
    var docId:String?=null
    var token: String? = null
    var proId:Long?=null
    var varId:Long?=null
    var proName:String?=null
    var varSize:Long?=null
    var varType:String?=null
    var variantList: List<ResponseProduct.Variant?>?=null
    var finalDate:String?=null


    //    val modelClasses: ArrayList<ManualOrder> = ArrayList<ManualOrder>()
    var selectedDate:String?=null
    var item:ArrayList<ResponseProduct>?=null
    var itemVar:ArrayList<ResponseProduct.Variant>?=null
    val modelClasses = ArrayList<ProductList?>()



    var mItemList: List<ManualOrder>?=null

    var penPrices:String?=null
    var totalPrices:String?=null

    var quantity:String?=null
    var pPrice:String?=null

    private val mTimePicker by lazy {
        TextViewDatePicker(requireContext(), setMaxDate = true, setMinDate = false) { date ->
            selectedDate = date
            val sdf = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
            val currentDateandTime: String = sdf.format(Date())
            val time  = CalendarUtils.formatDate(currentDateandTime,"yyyy.MM.dd G 'at' HH:mm:ss z","HH:mm:ss")
            finalDate = "${selectedDate} ${time}"
            mBinding.idEdtDate.setText(CalendarUtils.getMonthName(date))
        }
    }

    companion object {
        val TAG: String = ManualOrderFragment::class.java.simpleName
        fun getInstance(docId: String?) = ManualOrderFragment().also {
            it.arguments = bundleOf(BundleConstant.DOCTOR_ID to docId)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentManualOrderBinding {
        return FragmentManualOrderBinding
            .inflate(inflater, container, false)
    }

    override fun onViewInitialized() {
        buildUi()
        setListener()
    }

    private fun buildUi() {
        docId = arguments?.getString(BundleConstant.DOCTOR_ID)

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        token = PreferenceManager.getDefaultSharedPreferences(context)
            ?.getString(RestConstant.AUTH_TOKEN, "").toString()


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

        viewModel?.homeCategory?.observe(viewLifecycleOwner){
            when(it){
                is Result.Success->{
                    Log.d("data",it.data.toString())
                    item = it.data
//                    itemVar = it.data.getOrNull(0)?.variants as ArrayList<ResponseProduct.Variant>

//                    mProductAdapter.addItems(it.data.getOrNull(0)?.variants)

                    val Category = arrayOfNulls<String>(it.data.size!!)

                    for (i in 0 until it.data.size!!) {
                        Category[i]= it.data.get(i)?.title.toString()

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            Category
                        )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

                        mBinding.idEdtProduct.setAdapter(spinnerArrayAdapter)

                    }
                }
                is Result.Error ->
                {
                }

            }
        }

        viewModel?.manualOrder?.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {

                    baseActivity.onBackPressed()
                    baseActivity.showToast("You have successfully manual Ordered.")

                }

                is Result.Error -> {


                }
            }
        }

    }

    private fun setListener() {

        mBinding.idEdtTPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                totalPrices = mBinding.idEdtTPrice.text.toString()
            }
        })

        mBinding.idEdtPPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                penPrices = mBinding.idEdtPPrice.text.toString()
            }
        })

        mBinding.idEdtDate.setOnClickListener {
            mTimePicker.show()

        }

        mBinding.idEdtProduct.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {


                proId = item?.get(position)?.id
                proName = item?.get(position)?.title

                 variantList = item?.get(position)?.variants

                val Category =  arrayOfNulls<String>(variantList?.size!!)


                            for (i in 0 until variantList?.size!!) {

                                Category[i] = "${variantList?.get(i)?.size.toString()} ${variantList?.get(i)?.type.toString()}"
                                val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    requireContext(),
                                    R.layout.simple_spinner_item,
                                    Category
                                )
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view
                                mBinding.idEdtVariant.setAdapter(spinnerArrayAdapter)
                            }


            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        mBinding.idEdtVariant.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                position: Int, id: Long
            ) {

                varId = variantList?.get(position)?.id
                varSize = variantList?.get(position)?.size
                varType = variantList?.get(position)?.type

                Log.d("varid",varId.toString())

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        mBinding.idEdtQuant.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                quantity = mBinding.idEdtQuant.text.toString()
            }
        })

        mBinding.idEdtPri.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pPrice = mBinding.idEdtPri.text.toString()
            }
        })

        mBinding.idBtnAdd.setOnClickListener {

            if (pPrice?.isNotEmpty() == true && quantity?.isNotEmpty() == true)
            {
                modelClasses.add(
                    ProductList(pPrice?.toInt(),proId,quantity?.toInt(),varId,proName,varSize,varType)
                )

                Log.d("sizearray", modelClasses.size.toString()
                )

                Log.d("model",modelClasses.toString())

                var layoutManager = LinearLayoutManager(context)
                mBinding.idRvItem.setHasFixedSize(true)
                layoutManager.orientation = RecyclerView.HORIZONTAL
                mBinding.idRvItem.layoutManager = layoutManager

                var adapter = ManualOrderItemAdapter(modelClasses)
                mBinding.idRvItem.adapter = adapter

                mBinding.idEdtQuant.text.clear()
                mBinding.idEdtPri.text.clear()
            }
            else{
                baseActivity.showToast("Enter all field.")
            }

        }


        mBinding.idBtnCreate.setOnClickListener {

            if (totalPrices?.isNotEmpty() == true && docId?.isNotEmpty() == true &&
                finalDate?.isNotEmpty() == true && penPrices?.isNotEmpty() == true && modelClasses.isNotEmpty())
            {
                mReqAddManualOrder.apply {
                    totalprice = totalPrices?.toInt()
                    doctor = docId
                    date = finalDate
                    pendingprice = penPrices?.toInt()
                    val proList = ArrayList<ReqAddManualOrder.Product>()
                    for (i in 0..modelClasses.size-1){

                        proList?.add(ReqAddManualOrder.Product(price = modelClasses.get(i)?.price,
                            productid = modelClasses.get(i)?.productid,quantity = modelClasses.get(i)?.quantity,
                            variantid = modelClasses.get(i)?.variantid))

                    }
                    Log.d("prolist",proList.toString())
                    products = proList

                }
                token?.let { it1 -> viewModel.orderManual(it1,mReqAddManualOrder) }
            }
            else
            {
                baseActivity.showToast("Please fill all the fields.")
            }

        }

    }

}