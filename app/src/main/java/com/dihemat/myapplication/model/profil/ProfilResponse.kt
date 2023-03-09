package com.dihemat.myapplication.model.profil

import com.google.gson.annotations.SerializedName

data class ProfilResponse(

    @field:SerializedName("data")
	val data: ProfilModel? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Int? = null
)