package com.macaosoftware.util

sealed class MacaoResult<out T> {
    class Success<T>(val value: T) : MacaoResult<T>()
    class Error(val error: MacaoError) : MacaoResult<Nothing>()
}

interface MacaoError
