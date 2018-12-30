package com.ncl.common.domain.user


sealed class LoginEvent<T> {

    private var payload: T? = null

    fun setPayload(payload: T) {
        this.payload = payload
    }

    fun getPayload(): T? {
        return payload
    }

    class HamperSignUpStart<U> : LoginEvent<U>()
    class HamperSignUpFail<U> : LoginEvent<U>()
    class HamperSignUpSuccess<U> : LoginEvent<U>()
    class HamperLoginStart<U> : LoginEvent<U>()
    class HamperLoginFail<U> : LoginEvent<U>()
    class HamperLoginSuccess<U> : LoginEvent<U>()

}