package com.ncl.common.domain.auth

data class LoginFormResp(val error: Boolean,
                         val errorCode: Int?,
                         val errorInfo: String?,
                         val authToken: String?)