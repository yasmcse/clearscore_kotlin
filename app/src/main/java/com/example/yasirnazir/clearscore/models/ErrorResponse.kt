package com.example.yasirnazir.clearscore.models

import com.google.gson.annotations.SerializedName

/**
 * Created by yasirnazir on 3/14/18.
 */

class ErrorResponse {
    @SerializedName("error")
    lateinit var error: String


    constructor() {}


    constructor(error: String) {
        this.error = error
    }
}
