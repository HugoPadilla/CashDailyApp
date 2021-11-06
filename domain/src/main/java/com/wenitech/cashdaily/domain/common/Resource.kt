package com.wenitech.cashdaily.domain.common

// A generic class that contains data and status about loading this data.
sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable, val msg: String? = null) : Resource<T>()
}