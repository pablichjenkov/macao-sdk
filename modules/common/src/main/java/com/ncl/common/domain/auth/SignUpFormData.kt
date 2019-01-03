package com.ncl.common.domain.auth

data class SignUpFormData(
        val name: String,
        val email: String,
        val phone: String,
        val password: String,
        val zipcode: String)
