package com.dihemat.myapplication.model.produktoko

import com.google.gson.annotations.SerializedName

data class ProdukTokoResponse(

    @field:SerializedName("data")
	val data: List<ProdukTokoModel?>? = null,

    @field:SerializedName("message")
	val message: String? = null
)