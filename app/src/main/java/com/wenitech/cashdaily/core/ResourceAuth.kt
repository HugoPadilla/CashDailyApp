package com.wenitech.cashdaily.core

data class ResourceAuth<out T>(
        val status: Status,
        val dato: T?,
        val messenger: String?,
) {

    companion object {

        fun <T> init(): ResourceAuth<T> {
            return ResourceAuth(Status.INIT, null, null)
        }

        fun <T> loading(dato: T?): ResourceAuth<T> {
            return ResourceAuth(Status.LOADING, dato, null)
        }

        fun <T> success(dato: T): ResourceAuth<T> {
            return ResourceAuth(Status.SUCCESS, dato, null)
        }

        fun <T> collicion(msg: String, dato: T): ResourceAuth<T> {
            return ResourceAuth(Status.COLLICION, dato, msg)
        }

        fun <T> failed(msg: String, dato: T): ResourceAuth<T> {
            return ResourceAuth(Status.FAILED, dato, msg)
        }

        fun <T> error(messenge: String, dato: T?): ResourceAuth<T> {
            return ResourceAuth(Status.ERROR, dato, messenge)
        }
    }
}