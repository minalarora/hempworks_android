package com.hemp.works.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.navigation.NavigationView
import com.hemp.works.R
import com.hemp.works.dashboard.UserType

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

    @BindingAdapter("load_image")
    fun loadImage(view: ImageView, imageId: Int) {
        view.setImageResource(imageId)
    }

    @BindingAdapter("setMenu")
    fun setMenu(navigationView: NavigationView, userType: UserType) {
        navigationView.menu.clear();
        navigationView.inflateMenu(when(userType) {
            UserType.APPROVED -> R.menu.approved_doctor_menu
            UserType.PENDING -> R.menu.pending_doctor_menu
            UserType.ANONYMOUS -> R.menu.without_login_menu
        });
    }
