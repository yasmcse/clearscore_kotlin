package com.example.yasirnazir.clearscore.networking

import com.example.yasirnazir.clearscore.models.Response

import rx.Observable


/**
 * Created by yasirnazir on 3/14/18.
 */

class NetworkService(private val networkApi: NetworkApi) {

    val creditValues: Observable<Response>
        get() = networkApi.getCreditValues()
}