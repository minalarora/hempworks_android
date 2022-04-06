package com.hemp.works.dashboard.model

import com.hemp.works.login.data.model.Doctor

data class Chat(
    val id: String? = null,
    val doctor: Doctor? = null,
    val uniqueid: String? = null,
    val doctorseen: Boolean? = null,
    val adminseen: Boolean? = null,
    var messages: MutableList<Message>? = null
)
