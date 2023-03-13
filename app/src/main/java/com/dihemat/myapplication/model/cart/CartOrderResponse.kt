package com.dihemat.myapplication.model.cart

import com.google.gson.annotations.SerializedName

data class CartOrderResponse(

    @field:SerializedName("data")
	val data: List<CartOrderModel?>? = null,

    @field:SerializedName("message")
	val message: String? = null
)