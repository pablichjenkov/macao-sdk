package com.macaosoftware.util

sealed class MacaoResult<out T, out E> {
    class Success<T>(val value: T) : MacaoResult<T, Nothing>()
    class Error<E>(val error: E) : MacaoResult<Nothing, E>()
}
