package com.minal.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hoobio.base.BaseActivity
import com.minal.admin.databinding.ActivityAdminBinding
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.ui.AdminFragment

class AdminActivity : BaseActivity<ActivityAdminBinding>() {

    companion object {
        val TAG: String = AdminActivity::class.java.simpleName
        fun openActivity(context: Context, setFlag: Boolean = false) {
            context.startActivity(Intent(context, AdminActivity::class.java).also {
                if (setFlag) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            })
        }
    }

    override fun getViewBinding() = ActivityAdminBinding.inflate(layoutInflater)

    override fun onViewInitialized() {
        initFragment()


    }

    private fun initFragment() {
        replaceFragment(false,
            R.id.idFcvAdmin,
            AdminFragment.getInstance(),
            AdminFragment.TAG)
    }

}