package com.pablichj.incubator.amadeus.common

import AmadeusError

sealed class CallResult<T> {
    class Error<T>(val error: AmadeusError) : CallResult<T>()
    class Success<T>(val responseBody: T) : CallResult<T>()
}
