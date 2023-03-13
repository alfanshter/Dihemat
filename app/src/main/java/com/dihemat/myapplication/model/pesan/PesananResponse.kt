package com.dihemat.myapplication.model.pesan

import com.google.gson.annotations.SerializedName

data class PesananResponse(

    @field:SerializedName("data")
	val data: List<PesananModel?>? = null,

    @field:SerializedName("message")
	val message: String? = null
)