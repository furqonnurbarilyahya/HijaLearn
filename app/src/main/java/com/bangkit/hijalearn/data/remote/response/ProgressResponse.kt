package com.bangkit.hijalearn.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProgressResponse(

	@field:SerializedName("last_module")
	val lastModule: Int,

	@field:SerializedName("module")
	val module: List<ModuleItem>
)

data class ModuleItem(

	@field:SerializedName("module_id")
	val moduleId: Int,

	@field:SerializedName("totalSubModule")
	val totalSubModule: Int,

	@field:SerializedName("completed")
	val completed: Boolean,

	@field:SerializedName("subModuleDone")
	val subModuleDone: Int
)
