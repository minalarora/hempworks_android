package com.hemp.works.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.UserType
import com.hemp.works.login.data.model.Doctor
import de.hdodenhof.circleimageview.CircleImageView

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

    @BindingAdapter("setHeader")
    fun setHeader(navigationView: NavigationView, user: Doctor?) {
        if (user == null) navigationView.removeHeaderView(navigationView.getHeaderView(0))
        else {
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.username).text = user.name
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.email).text = user.email
            navigationView.getHeaderView(0).findViewById<CircleImageView>(R.id.profile)?.let {
                Glide.with(navigationView.context)
                    .load(user.profile)
                    .placeholder(R.drawable.doctor_placeholder)
                    .error(R.drawable.doctor_placeholder)
                    .apply(MyAppGlideModule.requestOptions)
                    .into(it);
            }
        }
    }



