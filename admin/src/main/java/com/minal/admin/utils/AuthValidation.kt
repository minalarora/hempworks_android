package com.minal.admin.utils

import android.content.Context
import com.minal.admin.R
import com.minal.admin.databinding.FragmentCreateCouponBinding
import com.minal.admin.ext_fun.showToast

object AuthValidation {

    fun couponValidation(
        mContext: Context,
        mBinding: FragmentCreateCouponBinding
    ): Boolean {

        if (mBinding.idEdtName.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_coupon_name))
            return false
        }
        if (mBinding.idEdtDis.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_discription))
            return false
        }

        if (mBinding.idEdtUse.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_canuse_value))
            return false
        }
        if (mBinding.idEdtValue.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_value))
            return false
        }

        if (!mBinding.idRbPublic.isChecked && !mBinding.idRbPrivate.isChecked ) {
            mContext.showToast(mContext.getString(R.string.please_select_type))
            return false
        }

        if (!mBinding.idRbPercentage.isChecked && !mBinding.idRbValue.isChecked
            && !mBinding.idRbCredit.isChecked && !mBinding.idRbQuantity.isChecked ) {
            mContext.showToast(mContext.getString(R.string.please_select_type))
            return false
        }


        return true
    }


    fun couponQuantityValidation(
        mContext: Context,
        mBinding: FragmentCreateCouponBinding
    ): Boolean {

        if (mBinding.idEdtName.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_coupon_name))
            return false
        }
        if (mBinding.idEdtDis.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_discription))
            return false
        }

        if (mBinding.idEdtUse.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_canuse_value))
            return false
        }
        if (mBinding.idEdtValue.text.isNullOrBlank()) {
            mContext.showToast(mContext.getString(R.string.please_enter_value))
            return false
        }


        return true
    }
}