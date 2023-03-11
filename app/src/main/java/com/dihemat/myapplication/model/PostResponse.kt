package com.dihemat.myapplication.model

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
