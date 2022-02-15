package com.hemp.works.dashboard.prescription.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class PrescriptionRemoteDataSource @Inject constructor(private val service: PrescriptionService): BaseDataSource() {
}