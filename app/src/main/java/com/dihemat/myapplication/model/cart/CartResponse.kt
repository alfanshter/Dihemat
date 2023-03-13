package com.dihemat.myapplication.model.cart

import com.google.gson.annotations.SerializedName

data class CartResponse(

    @field:SerializedName("data")
	val data: List<CartModel?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("harga_total")
    val harga_total: Int? = null
)