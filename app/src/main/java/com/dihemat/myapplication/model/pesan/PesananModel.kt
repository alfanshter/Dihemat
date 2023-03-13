package com.dihemat.myapplication.model.pesan

import com.google.gson.annotations.SerializedName

data class PesananModel(

	@field:SerializedName("kode_pesanan")
	val kodePesanan: String? = null,

	@field:SerializedName("is_status")
	val isStatus: Int? = null,

	@field:SerializedName("alamat_customer")
	val alamatCustomer: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("ongkir")
	val ongkir: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("harga_total")
	val hargaTotal: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("toko_id")
	val tokoId: String? = null
)