package com.minal.admin.data.response

import androidx.databinding.ObservableBoolean
import java.util.*

data class Message(
    val message: String? = null,
    val type: String? = null,
    val date: Date? = null,
    val isDoctor: Boolean? = null
) {
    var isSent: ObservableBoolean = ObservableBoolean(true)
}