package com.bangkit.hijalearn.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("correct")
	val correct: Boolean,

	@field:SerializedName("probability")
	val probability: Int,

	@field:SerializedName("message")
	val message: String
)
