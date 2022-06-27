package com.minal.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hoobio.base.BaseActivity
import com.minal.admin.databinding.ActivityAdminBinding
import com.minal.admin.ext_fun.replaceFragment
import com.minal.admin.ui.AdminFragment
import com.minal.admin.ui.Logout
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Logger

class AdminActivity : BaseActivity<ActivityAdminBinding>(),Logout{

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
        initDI()
        initFragment()


    }

    private fun initFragment() {
        replaceFragment(false,
            R.id.idFcvAdmin,
            AdminFragment.getInstance(),
            AdminFragment.TAG)
    }

    private fun initDI() {
        startKoin {
            koin._logger = koinLogger()
            //declare application context
            androidContext(this@AdminActivity)
            //declare all modules here
        }
    }

    /**
     * [androidLogger] will display logs in release mode
     * [koinLogger] will display logs only when [AppConfig.LOG_ENABLE] is @true
     * else it will return empty log*/
    private fun koinLogger(): Logger {
        return if (false)
            org.koin.android.logger.AndroidLogger() else
            EmptyLogger()
    }

    override fun logoutAdmin() {
        Log.d("d","d")
    }


}