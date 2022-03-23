package com.hemp.works.dashboard.address.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.login.data.model.Address
import retrofit2.Response
import retrofit2.http.*

interface AddressService {

    @GET("/v1/address")
    suspend fun getAddressList(): Response<List<Address>>

    @POST("/v1/address")
    suspend fun addAddress(@Body address: Address): Response<BooleanResponse>

    @PATCH("/v1/address")
    suspend fun removeAddress(@Query("id") addressId: Long, @Body body: HashMap<String,Boolean>): Response<BooleanResponse>

    @PATCH("/v1/address")
    suspend fun updateAddress(@Query("id") addressId: Long, @Body body: Address): Response<BooleanResponse>
}