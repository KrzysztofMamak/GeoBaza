package com.mamak.geobaza.network.connection

import com.mamak.geobaza.network.firebase.GeoBazaException

class Resource<T> private constructor(
        private val status: Status,
        val data: T?,
        val exception: GeoBazaException?) {
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

        fun <T> error(geoBazaException: GeoBazaException): Resource<T> {
            return Resource(Status.ERROR, null, geoBazaException)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}