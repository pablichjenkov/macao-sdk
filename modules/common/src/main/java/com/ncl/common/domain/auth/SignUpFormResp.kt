package com.ncl.common.domain.auth

data class SignUpFormResp(val error: Boolean,
                          val errorCode: Int,
                          val errorInfo: String?,
                          val signupResult: Boolean?)