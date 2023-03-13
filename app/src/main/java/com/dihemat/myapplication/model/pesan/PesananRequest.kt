package com.dihemat.myapplication.model.pesan

import com.google.gson.annotations.SerializedName

data class PesananRequest(

	@field:SerializedName("alamat_customer")
	val alamatCustomer: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("ongkir")
	val ongkir: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("harga_total")
	val hargaTotal: Int? = null,

	@field:SerializedName("toko_id")
	val tokoId: Int? = null
)