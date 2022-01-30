package com.hemp.works.utils

import android.text.TextUtils
import java.util.regex.Pattern


val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && EMAIL_ADDRESS_PATTERN.matcher(this).matches()
}