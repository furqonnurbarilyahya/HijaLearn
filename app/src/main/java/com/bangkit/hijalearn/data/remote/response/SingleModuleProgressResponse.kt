package com.bangkit.hijalearn.data.remote.response

import com.google.gson.annotations.SerializedName

data class SingleModuleProgressResponse(

	@field:SerializedName("totalSubModule")
	val totalSubModule: Int,

	@field:SerializedName("completed")
	val completed: Boolean,

	@field:SerializedName("subModuleDone")
	val subModuleDone: Int
)
