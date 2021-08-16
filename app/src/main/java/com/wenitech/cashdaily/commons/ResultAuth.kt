package com.wenitech.cashdaily.commons

data class ResultAuth<out T>(
    val status: Status,
    val dato: T?,
    var messenger: String?,
) {

    companion object {

        fun <T> loading(dato: T?): ResultAuth<T> {
            return ResultAuth(Status.LOADING, dato, null)
        }

        fun <T> success(dato: T): ResultAuth<T> {
            return ResultAuth(Status.SUCCESS, dato, null)
        }

        fun <T> collicion(msg: String, dato: T): ResultAuth<T> {
            return ResultAuth(Status.COLLICION, dato, msg)
        }

        fun <T> failed(msg: String, dato: T): ResultAuth<T> {
            return ResultAuth(Status.FAILED, dato, msg)
        }
    }
}
