package com.minal.admin.ext_fun

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hoobio.base.BaseActivity
import java.io.Serializable
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec


//Hide View
fun View.hide() {
    this.visibility = View.GONE
}

//invisible
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

//show view
fun View.show() {
    this.visibility = View.VISIBLE
}

//show Progress in fragment
val Fragment.baseActivity: BaseActivity<*>
    get() = (requireActivity() as BaseActivity<*>)

//replace fragment
fun Fragment.replaceFragment(
    isAddToBackStack: Boolean = false,
    idContainer: Int,
    fragment: Fragment,
    tag: String
) {
    parentFragmentManager.beginTransaction().apply {
        if (isAddToBackStack) {
            addToBackStack(tag)
        }
        replace(idContainer, fragment, tag).commit()
    }
}

//replace Activity
fun AppCompatActivity.replaceFragment(
    isAddToBackStack: Boolean = false,
    idContainer: Int,
    fragment: Fragment,
    tag: String
) {
    supportFragmentManager.beginTransaction().apply {
        if (isAddToBackStack) {
            addToBackStack(tag)
        }
        replace(idContainer, fragment, tag).commit()
    }
}


//show toast
fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Handler(Looper.getMainLooper()).post {
        message?.let {
            Toast.makeText(this, message, duration).show()
        }
    }

}

//fragment add
fun Fragment.addFragment(
    isAddToBackStack: Boolean = false,
    idContainer: Int,
    fragment: Fragment,
    tag: String
) {
    parentFragmentManager.beginTransaction().apply {
        if (isAddToBackStack) {
            addToBackStack(tag)
        }
        add(idContainer, fragment, tag).commit()
    }

}


fun String?.addBearer(): String {
    return if (this.isNullOrBlank()) return ""
    else "Bearer $this"
}
