package com.example.yasirnazir.clearscore.models

/**
 * Created by yasirnazir on 3/18/18.
 */

import com.google.gson.annotations.SerializedName

open class APIErrors {

    @SerializedName("errors")
    val errors: List<Error>? = null

    @SerializedName("exception")
    val exception: List<Error>? = null
}
