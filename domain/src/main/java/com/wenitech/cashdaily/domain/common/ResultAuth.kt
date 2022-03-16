package com.wenitech.cashdaily.domain.common

sealed class ResultAuth<out T> {
    data class Collision(val msg: String? = null) : ResultAuth<Nothing>()
    data class Failed(val msg: String? = null, val throwable: Throwable? = null) :
        ResultAuth<Nothing>()

    object Loading : ResultAuth<Nothing>()
    data class Success<T>(val data: T? = null) : ResultAuth<T>()
}
