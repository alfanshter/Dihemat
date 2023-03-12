package com.dihemat.myapplication.model

import com.google.gson.annotations.SerializedName

data class PostCartRequest(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("menu_id")
	val menuId: Int? = null,

	@field:SerializedName("toko_id")
	val toko_id: Int? = null
)
