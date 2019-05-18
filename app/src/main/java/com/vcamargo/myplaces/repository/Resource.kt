package com.vcamargo.myplaces.repository

class Resource<T> {
    val status: Status
    val data: T?
    val message: String?

    constructor(status: Status, data: T, message: String) {
        this.status = status
        this.data = data
        this.message = message
    }

    constructor(status: Status) {
        this.status = status
        this.data = null
        this.message = null
    }

    constructor(status: Status, message: String) {
        this.status = status
        this.data = null
        this.message = message
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, Status.SUCCESS.toString())
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}