package com.dihemat.myapplication.model.cart

import com.google.gson.annotations.SerializedName

data class CartOrderModel(

	@field:SerializedName("kode_pesanan")
	val kodePesanan: String? = null,

	@field:SerializedName("is_status")
	val isStatus: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("menu_id")
	val menuId: Int? = null,

	@field:SerializedName("toko_id")
	val tokoId: Int? = null
)