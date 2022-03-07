package com.hemp.works.dashboard.tnl.ui.adapters

import android.annotation.SuppressLint
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.NewsLetter
import java.text.SimpleDateFormat

class NewsLetterViewModel(private val newsLetter: NewsLetter) {

    val image: String = newsLetter.image ?: ""

    val imageVisibility: Boolean = !newsLetter.image.isNullOrBlank()

    val title: String = newsLetter.title?.let {
        if (newsLetter.title.length >  70) {
            newsLetter.title.substring(0, 70) + " read more..."
        } else {
            newsLetter.title
        }
    } ?: "No Title Defined!"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(newsLetter.date!!)

}