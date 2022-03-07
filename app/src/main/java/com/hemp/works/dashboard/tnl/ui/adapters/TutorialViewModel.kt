package com.hemp.works.dashboard.tnl.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.databinding.ObservableBoolean
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Tutorial
import java.text.SimpleDateFormat
import java.util.*

class TutorialViewModel(private val tutorial: Tutorial) {

        val description: Spanned = HtmlCompat.fromHtml(tutorial.description?.let {
            if (tutorial.description.length >  150) {
                tutorial.description.substring(0, 150) + "... <font color='#0000EE'>read more</font>"
            } else {
                tutorial.description
            }
        } ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)

        val descriptionVisibility = tutorial.type == "video"


        val isReadMore: Boolean = tutorial.description?.let { return@let tutorial.description.length > 150 } ?: false


        val coverVisibility = if (tutorial.type == "video") {
            tutorial.thumbnail != null
        } else false

        val coverImageUrl = tutorial.thumbnail ?: ""

        val title: Spanned = if (tutorial.type == "video") {
            HtmlCompat.fromHtml(tutorial.title ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            description
        }

         val pdfVisibility = tutorial.type == "pdf"

        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
        val date: String = dateFormat.format(tutorial.date!!)



}