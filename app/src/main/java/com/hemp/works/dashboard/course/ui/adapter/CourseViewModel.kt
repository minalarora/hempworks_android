package com.hemp.works.dashboard.course.ui.adapter

import android.annotation.SuppressLint
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Course
import com.hemp.works.dashboard.model.NewsLetter
import java.text.SimpleDateFormat

class CourseViewModel(private val course: Course) {

    val image: String = course.image ?: ""

    val imageVisibility: Boolean = !course.image.isNullOrBlank()

    val title: String = course.title?.let {
        if (course.title.length >  70) {
            course.title.substring(0, 70) + " read more..."
        } else {
            course.title
        }
    } ?: "No Title Defined!"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(course.date!!)

}