package com.hemp.works.dashboard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cashfree.pg.CFPaymentService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.hemp.works.R
import com.hemp.works.databinding.ActivityDashboardBinding
import com.hemp.works.di.injectViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

const val UPDATE_REQUEST_CODE = 101
class DashboardActivity : AppCompatActivity(), HasSupportFragmentInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: DashboardSharedViewModel

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    private lateinit var appUpdateManager : AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        sharedViewModel = injectViewModel(viewModelFactory)
        navController = this.findNavController(R.id.nav_host_fragment_container)
        checkForAppUpdate()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CFPaymentService.REQ_CODE) {
            sharedViewModel.updatePaymentBundle(data?.extras)
        }
    }

    private fun checkForAppUpdate() {

        appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo


        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {

                MaterialAlertDialogBuilder(this)
                    .setTitle("NEW UPDATE AVAILABLE")
                    .setMessage("KINDLY UPDATE TO THE LATEST VERSION OF THIS APP.")
                    .setNegativeButton("LATER") { dialog, which ->

                    }
                    .setPositiveButton("UPDATE NOW") { dialog, which ->
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=com.hemp.works")
                            )
                        )
                    }
                    .show()
            }
        }



    }

}