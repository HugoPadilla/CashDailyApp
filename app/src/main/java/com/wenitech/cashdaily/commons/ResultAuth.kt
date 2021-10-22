package com.wenitech.cashdaily.commons

data class ResultAuth<out T>(
    val status: Status,
    val data: T?,
    var messenger: String?,
) {

    companion object {

        fun <T> loading(data: T?): ResultAuth<T> {
            return ResultAuth(Status.LOADING, data, null)
        }

        fun <T> success(data: T): ResultAuth<T> {
            return ResultAuth(Status.SUCCESS, data, null)
        }

        fun <T> collision(msg: String, data: T): ResultAuth<T> {
            return ResultAuth(Status.COLLICION, data, msg)
        }

        fun <T> failed(msg: String, data: T): ResultAuth<T> {
            return ResultAuth(Status.FAILED, data, msg)
        }
    }
}
