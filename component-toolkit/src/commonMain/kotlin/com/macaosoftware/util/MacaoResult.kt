package com.macaosoftware.util

sealed class MacaoResult<out T> {
    class Success<T>(val value: T) : MacaoResult<T>()
    class Error(val error: MacaoError) : MacaoResult<Nothing>()
}

sealed class MacaoResult2<out S, out E> {
    class Success<T>(val value: T) : MacaoResult2<T, Nothing>()
    class Error<V>(val error: V) : MacaoResult2<Nothing, V>()
}

interface MacaoError
