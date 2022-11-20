package com.hemp.works.dashboard.model

import java.util.*
import kotlin.collections.ArrayList

data class Prescription constructor(
    var id: Long? = null,
    var prescription: String? = null,
    var type: String? = null,
    var description: String? = null,
    var date: Date? = null,
    var prescriptions: ArrayList<PrescriptionMedia> = arrayListOf()
)

data class PrescriptionMedia constructor(
    var prescription: String? = null,
    var type: String? = null,
)