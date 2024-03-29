package com.base.core.datasource.model

import com.google.gson.annotations.SerializedName

data class ResponseDataError(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
