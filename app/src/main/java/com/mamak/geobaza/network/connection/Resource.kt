package com.mamak.geobaza.network.connection

import java.lang.Exception

class Resource<T> private constructor(
        private val status: Status,
        val data: T?,
        val exception: Exception?,
        message: String? = null) {
    val isSuccess: Boolean
        get() = status === Status.SUCCESS

    val isLoading: Boolean
        get() = status === Status.LOADING

    val isLoaded: Boolean
        get() = status !== Status.LOADING

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, null, message)
        }

        fun <T> error(exception: Exception?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}