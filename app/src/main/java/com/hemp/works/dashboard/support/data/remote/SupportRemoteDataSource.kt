package com.hemp.works.dashboard.support.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.model.RequestMessage
import com.hemp.works.dashboard.product.data.remote.ProductService
import javax.inject.Inject

class SupportRemoteDataSource @Inject constructor(private val service: SupportService): BaseDataSource() {

    suspend fun initChat(id: String) = getResult { service.initChat(id) }

    suspend fun sendMessage(id: String, message: String) = getResult { service.sendMessage(
        RequestMessage(id, message)
    ) }


}