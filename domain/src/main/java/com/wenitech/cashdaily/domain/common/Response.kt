package com.wenitech.cashdaily.domain.common

// A generic class that contains data and status about loading this data.
sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val throwable: Throwable, val msg: String? = null) : Response<Nothing>()
}