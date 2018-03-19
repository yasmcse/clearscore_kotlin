package com.example.yasirnazir.clearscore.networking

import com.example.yasirnazir.clearscore.models.APIErrors
import com.example.yasirnazir.clearscore.models.ApiError
import com.google.gson.Gson

import java.net.UnknownHostException

import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException


/**
 * Created by yasirnazir on 3/14/18.
 *
 *
 * Transforms errors received from network requests into [ApiError]s which can be displayed to the user.
 */

class NetworkErrorMapper {

    fun mapError(throwable: Throwable): ApiError {
        if (throwable is HttpException) {
            if (throwable.response() != null) {
                val apiErrors = parseError(throwable.response())
                var firstErrorMessage: String? = null
                if (containsErrors(apiErrors)) {
                    firstErrorMessage = apiErrors.errors!![0].message

                }
                return ApiError(throwable.response().code(), firstErrorMessage!!)
            } else {
                return ApiError(throwable.response().code(), null!!)
            }
        } else {
            return ApiError(1001, DEFAULT_ERROR_MESSAGE)
        }
    }

    private fun parseError(response: Response<*>): APIErrors {
        val error: APIErrors?

        try {
            error = Gson().fromJson(response.errorBody().string(), APIErrors::class.java)
        } catch (e: Exception) {
            return APIErrors()
        }

        return error ?: APIErrors()
    }

    private fun containsErrors(apiErrors: APIErrors): Boolean {
        return apiErrors.errors != null && !apiErrors.errors.isEmpty()
    }

    companion object {
        val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
        val NO_CONNECTION_ERROR = "No Internet Connection!"
    }
}
