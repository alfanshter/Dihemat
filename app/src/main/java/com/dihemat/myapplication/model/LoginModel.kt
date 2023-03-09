package com.dihemat.myapplication.model

import com.google.gson.annotations.SerializedName

data class LoginModel(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("is_status_toko")
	val isStatusToko: Int? = null,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("nama_toko")
	val namaToko: Any? = null,

	@field:SerializedName("alamat")
	val alamat: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)