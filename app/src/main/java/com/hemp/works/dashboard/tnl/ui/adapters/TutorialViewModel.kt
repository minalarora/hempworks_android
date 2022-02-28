package com.hemp.works.dashboard.tnl.ui.adapters

import android.annotation.SuppressLint
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Tutorial
import java.text.SimpleDateFormat

class TutorialViewModel(private val tutorial: Tutorial) {

        val description: String = tutorial.description?.let {
            if (tutorial.description.length >  70) {
                tutorial.description.substring(0, 70) + " read more..."
            } else {
                tutorial.description
            }
        } ?: ""

        val descriptionVisibility = tutorial.type == "video"

        val coverVisibility = if (tutorial.type == "video") {
            tutorial.thumbnail != null
        } else false

        val coverImageUrl = tutorial.thumbnail ?: ""

        val title: String = if (tutorial.type == "video") {
            tutorial.title ?: ""
        } else {
            tutorial.description ?: ""
        }

         val pdfVisibility = tutorial.type == "pdf"

        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
        val date: String = dateFormat.format(tutorial.date!!)



}