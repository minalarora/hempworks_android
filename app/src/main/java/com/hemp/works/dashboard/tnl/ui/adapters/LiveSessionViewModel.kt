package com.hemp.works.dashboard.tnl.ui.adapters

import android.annotation.SuppressLint
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.LiveSession
import java.text.SimpleDateFormat

class LiveSessionViewModel(private val liveSession: LiveSession) {

    val title: String = liveSession.title ?: "No Title Defined!"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(liveSession.date!!)
}