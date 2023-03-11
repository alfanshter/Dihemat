package com.dihemat.myapplication.model

import com.google.gson.annotations.SerializedName

data class TokoTerdekatModel(

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("nama_toko")
	val namaToko: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("is_status_toko")
	val isStatusToko: Int? = null,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)