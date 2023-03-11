package com.dihemat.myapplication.model.produktoko

import com.google.gson.annotations.SerializedName

data class ProdukTokoModel(

	@field:SerializedName("nama_produk")
	val namaProduk: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

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
	val id: Int? = null
)