package com.hemp.works.utils

import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.UserType
import com.hemp.works.dashboard.payment.ui.PaymentStatus
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

    @BindingAdapter("setToolbarMenu")
    fun setToolbarMenu(toolbar: Toolbar, userType: UserType) {
        toolbar.menu.clear();
        toolbar.inflateMenu(when(userType) {
            UserType.APPROVED -> R.menu.toolbar_user_menu_approved
            UserType.PENDING ->  R.menu.toolbar_user_menu
            UserType.ANONYMOUS ->  R.menu.toolbar_user_menu
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

    @BindingAdapter("glideImage")
    fun setImage(imageView: ImageView, url: String?) {

        Glide.with(imageView.context)
            .load(url)
            .placeholder(ColorDrawable(ContextCompat.getColor(imageView.context, R.color.white)))
            .error(ColorDrawable(ContextCompat.getColor(imageView.context, R.color.white)))
            .apply(MyAppGlideModule.requestOptions)
            .into(imageView);
    }

    @BindingAdapter("glideImage")
    fun setImage(imageView: CircleImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_profile_loading)
            .error(R.drawable.doctor_placeholder)
            .apply(MyAppGlideModule.requestOptions)
            .into(imageView);
    }

    @BindingAdapter("strike")
    fun setStrikeText(textView: TextView, visible: Boolean) {
        textView.strike = visible
    }

    inline var TextView.strike: Boolean
        set(visible) {
            paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG



    @BindingAdapter("paymentBackground")
    fun setPaymentBackground(view: ConstraintLayout, paymentStatus: PaymentStatus) {
        when(paymentStatus) {
            is PaymentStatus.NONE -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.grey_CDCDCD)
            )
            is PaymentStatus.CREDIT_PENDING -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.yellow)
            )
            is PaymentStatus.ORDER_PENDING -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.yellow)
            )
            is PaymentStatus.CREDIT_COMPLETED -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.green)
            )
            is PaymentStatus.ORDER_COMPLETED -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.green)
            )
            is PaymentStatus.CREDIT_FAILED -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.red)
            )
            is PaymentStatus.ORDER_FAILED -> view.setBackgroundColor(
                ContextCompat.getColor(view.context, R.color.red)
            )
        }
    }

    @BindingAdapter("paymentAnimation")
    fun setPaymentAnimation(view: LottieAnimationView, paymentStatus: PaymentStatus) {
        when(paymentStatus) {
            is PaymentStatus.NONE -> view.visibility = View.GONE
            is PaymentStatus.CREDIT_PENDING, is PaymentStatus.ORDER_PENDING -> {
                view.setAnimation("pending.json")
                view.playAnimation()
                view.visibility = View.VISIBLE
            }
            is PaymentStatus.CREDIT_COMPLETED, is PaymentStatus.ORDER_COMPLETED -> {
                view.setAnimation("completed.json")
                view.playAnimation()
                view.visibility = View.VISIBLE
            }
            is PaymentStatus.CREDIT_FAILED,is PaymentStatus.ORDER_FAILED ->  {
                view.setAnimation("failed.json")
                view.playAnimation()
                view.visibility = View.VISIBLE
            }
        }
    }

    @BindingAdapter("paymentTitle")
    fun setPaymentTitle(view: TextView, paymentStatus: PaymentStatus) {
        when(paymentStatus) {
            PaymentStatus.NONE -> view.visibility = View.GONE
            is PaymentStatus.CREDIT_PENDING, is PaymentStatus.ORDER_PENDING -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_pending)
            }
            is PaymentStatus.CREDIT_COMPLETED, is PaymentStatus.ORDER_COMPLETED -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_completed)
            }
            is PaymentStatus.CREDIT_FAILED, is  PaymentStatus.ORDER_FAILED ->  {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_failed)
            }
        }
    }

    @BindingAdapter("paymentDesc")
    fun setPaymentDescription(view: TextView, paymentStatus: PaymentStatus) {
        when(paymentStatus) {
            is PaymentStatus.NONE -> view.visibility = View.GONE
            is PaymentStatus.CREDIT_PENDING -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_pending_desc)
            }
            is PaymentStatus.ORDER_PENDING -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_pending_desc)
            }
            is PaymentStatus.CREDIT_COMPLETED -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_completed_desc,  paymentStatus.amount.toString(), paymentStatus.id.toString())
            }
            is PaymentStatus.ORDER_COMPLETED -> {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_completed_desc,  paymentStatus.amount.toString(), paymentStatus.id.toString())
            }
            is PaymentStatus.CREDIT_FAILED ->  {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_failed_desc, paymentStatus.amount.toString(), paymentStatus.id.toString())
            }
            is PaymentStatus.ORDER_FAILED ->  {
                view.visibility = View.VISIBLE
                view.text = view.context.getString(R.string.trans_failed_desc, paymentStatus.amount.toString(), paymentStatus.id.toString())
            }
        }
    }




