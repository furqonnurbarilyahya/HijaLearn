package com.bangkit.hijalearn.model

data class User (
    val username: String,
    val email: String,
    val photoUrl: String? = null,
    val token: String,
    val isLogin: Boolean = false
)