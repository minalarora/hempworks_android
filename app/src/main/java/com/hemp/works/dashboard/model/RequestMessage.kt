package com.hemp.works.dashboard.model


data class RequestMessage(
    val id: String,
    val message: String,
    val type: String = "TEXT",
    val isDoctor: Boolean = true
)