package com.hemp.works.utils

import android.view.View
import androidx.databinding.BindingAdapter

class BindingAdapters {


    @BindingAdapter("hideIfFalse")
    fun hideIfFalse(view: View, boo: Boolean) {
        if (boo) view.visibility = View.VISIBLE else view.visibility = View.GONE
    }

    @BindingAdapter("invisibleIfFalse")
    fun invisibleIfFalse(view: View, boo: Boolean) {
        if (boo) view.visibility = View.VISIBLE else view.visibility = View.INVISIBLE
    }

    @BindingAdapter("hideIfTrue")
    fun hideIfTrue(view: View, boo: Boolean) {
        if (!boo) view.visibility = View.VISIBLE else view.visibility = View.GONE
    }

    @BindingAdapter("invisibleIfTrue")
    fun invisibleIfTrue(view: View, boo: Boolean) {
        if (!boo) view.visibility = View.VISIBLE else view.visibility = View.INVISIBLE
    }
}