package com.hemp.works.dashboard.home.ui.adapters

import com.hemp.works.dashboard.model.Blog

class BlogViewModel(private val blog: Blog) {

    val image: String = blog.image ?: ""

    val title: String = blog.title.toString()


}