package com.hemp.works.dashboard.model

import java.util.*

data class Prescription constructor(
    var id: Long? = null,
    var prescription: String? = null,
    var type: String? = null,
    var description: String? = null,
    var date: Date? = null
)
