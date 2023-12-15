package com.bangkit.hijalearn.model

import com.google.gson.annotations.SerializedName

data class SurahResponse(

	@field:SerializedName("SurahResponse")
	val surahResponse: List<SurahResponseItem>
)

data class SurahResponseItem(

	@field:SerializedName("ar")
	val ar: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("nomor")
	val nomor: String,

	@field:SerializedName("tr")
	val tr: String
)
