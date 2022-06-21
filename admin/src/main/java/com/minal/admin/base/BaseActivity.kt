package com.hoobio.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.minal.admin.utils.DialogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Logger
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseInterface,CoroutineScope {


    lateinit var mBinding: VB

    private val mProgressDialog: Dialog by lazy { DialogUtils.loader(this) }




    var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
//        if (savedInstanceState != null)
//            SplashActivity.openActivity(this)

        mBinding = getViewBinding()
        setContentView(mBinding.root)
        onViewInitialized()
    }

    /**
     * It returns [ViewBinding] [VB] which is assigned to [mViewBinding] and used in [onCreate]
     */
    abstract fun getViewBinding(): VB

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode,resultCode,data)
        }
    }
    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            if (isTaskRoot) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                this.doubleBackToExitPressedOnce = true
                Handler(Looper.getMainLooper())
                        .postDelayed(Runnable { doubleBackToExitPressedOnce = false },
                                2000)
            } else {
                super.onBackPressed()
            }
        }
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view: View? = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            val w: View? = currentFocus
            val location = IntArray(2)
            w?.let {
                it.getLocationOnScreen(location)
                val x: Float = event.rawX + w.left - location[0]
                val y: Float = event.rawY + w.top - location[1]
                if (event.action == MotionEvent.ACTION_DOWN
                    && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
                ) {
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
                }
            }
        }
        return ret
    }



    /**
     * Perform operation in background */
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    fun getCurrentVisibleFragment() : BaseFragment<*>
    {
        var visibleFragment: BaseFragment<*>? = null
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                visibleFragment = fragment as BaseFragment<*>
                break
            }
        }
        return visibleFragment!!
    }



    /**Hide and show loading dialog
     * [showProgress] & [hideProgress]
     * */
    fun showProgress() {
        mProgressDialog.show()
    }

    fun hideProgress() {
        mProgressDialog.dismiss()
    }



}