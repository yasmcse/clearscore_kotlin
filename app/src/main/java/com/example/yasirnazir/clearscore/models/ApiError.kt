package com.example.yasirnazir.clearscore.models

/**
 * Created by yasirnazir on 3/18/18.
 */


/**
 * A representation of an error that occurred while making an API request. Contains the HTTP status
 * code returned from the server (if applicable) and the first error message returned from the server
 * (if applicable).
 */
open class ApiError : RuntimeException {
    val responseCode: Int
    val errorMessage: String?

    constructor() {
        this.responseCode = -1
        this.errorMessage = null
    }

    constructor(responseCode: Int, errorMessage: String) {
        this.responseCode = responseCode
        this.errorMessage = errorMessage
    }

    fun hasErrorMessage(): Boolean {
        return errorMessage != null
    }


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val apiError = o as ApiError?

        return if (responseCode != apiError!!.responseCode) false else errorMessage == apiError.errorMessage
    }

    override fun hashCode(): Int {
        var result = responseCode
        result = 31 * result + errorMessage!!.hashCode()
        return result
    }

    override fun toString(): String {
        return "ApiError{" +
                "responseCode=" + responseCode +
                ", errorMessage='" + errorMessage + '\''.toString() +
                '}'.toString()
    }

    companion object {
        fun getMessage(apiError: ApiError): String? {
           return apiError.errorMessage
       }
    }
}