package com.kpit.scenichiking.data

import com.kpit.scenichiking.data.Resource.Status.ERROR
import com.kpit.scenichiking.data.Resource.Status.LOADING
import com.kpit.scenichiking.data.Resource.Status.SUCCESS

class Resource<T> private constructor(
    val status: Status,
    val data: T?,
    val throwable: Throwable?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                SUCCESS,
                data,
                null
            )
        }

        fun <T> error(throwable: Throwable): Resource<T> {
            return Resource(
                ERROR,
                null,
                throwable
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                LOADING,
                data,
                null
            )
        }
    }
}
