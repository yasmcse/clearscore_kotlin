package com.example.yasirnazir.clearscore.models

/**
 * Created by yasirnazir on 3/18/18.
 */

import com.google.gson.annotations.SerializedName

class Error {

    @SerializedName("code")
    val code: Int = 0

    @SerializedName("message")
    val message: String? = null

}
