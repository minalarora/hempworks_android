package com.hemp.works.dashboard.cart.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Cart
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface CartService {

    @PATCH("/v1/cart/addproduct")
    suspend fun addProduct(@Body product: RequestProduct) : Response<BooleanResponse>

    @PATCH("/v1/cart/addproduct")
    suspend fun addQuantity(@Body product: RequestProduct) : Response<BooleanResponse>

    @GET("/v1/cart")
    suspend fun getCart() : Response<Cart>

    @PATCH("/v1/cart/deleteproduct")
    suspend fun deleteProduct(@Body product: RequestProduct) : Response<BooleanResponse>

    @PATCH("/v1/cart/coupon")
    suspend fun applyCoupon(@Body body: HashMap<String,String>) : Response<BooleanResponse>

    @PATCH("/v1/cart/empty")
    suspend fun emptyCart() : Response<BooleanResponse>

    @GET("/v1/coupon")
    suspend fun getCouponList() : Response<List<Coupon>>


}