package com.example.yasirnazir.clearscore.base_classes

/**
 * Created by yasirnazir on 3/18/18.
 */


import java.lang.reflect.Type

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers


/**
 * Ensures that Retrofit requests are made on the io thread and responses
 * are observed on Android's main thread so that the view can be updated.
 */
class RxThreadCallAdapterFactory : CallAdapter.Factory() {
    internal var rxFactory = RxJavaCallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*>? {
        val callAdapter = rxFactory.get(returnType, annotations, retrofit) as CallAdapter<Observable<*>>
        return if (callAdapter != null) RxThreadCallAdapter(callAdapter) else null
    }

    private inner class RxThreadCallAdapter(internal var rxCallAdapter: CallAdapter<Observable<*>>) : CallAdapter<Observable<*>> {

        override fun responseType(): Type {
            return rxCallAdapter.responseType()
        }

        override fun <T> adapt(call: Call<T>): Observable<*> {
            return rxCallAdapter.adapt(call)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
