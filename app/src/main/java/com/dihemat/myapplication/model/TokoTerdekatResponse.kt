package com.dihemat.myapplication.model

import com.google.gson.annotations.SerializedName

data class TokoTerdekatResponse(

	@field:SerializedName("data")
	val data: List<TokoTerdekatModel?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)