package com.example.yasirnazir.clearscore.base_classes

/**
 * Created by yasirnazir on 3/18/18.
 */

import com.example.yasirnazir.clearscore.models.ApiError
import com.example.yasirnazir.clearscore.models.Error
import com.example.yasirnazir.clearscore.models.GenericModel
import com.example.yasirnazir.clearscore.models.InvalidSessionError
import com.example.yasirnazir.clearscore.networking.NetworkErrorMapper

import rx.Observable
import rx.functions.Func0
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

import rx.Observable.empty
import rx.functions.Action1


/**
 * A reusable component for loading data from the server. This is intended for GET requests.
 * The [Presenter] of the screen can subscribe to
 * the following three observables in order to update the UI with the progress:
 * [.observeLoading], [.observeData] and [.observeErrors].
 *
 *
 * When subscribing, if some data has already been fetched, this cached data will be returned,
 * rather than needing to call the server again. If an explicit refresh is required, the
 * [.refresh] method can be called.
 *
 * @param <T> A model object representing the type of data that we are fetching
</T> */
open class Fetcher<T : GenericModel>(private val requestFunction: Func0<Observable<T>>, private val cache: Cache<T>, private val requestPath: String) {
    private val ERROR_CODE_USER_NOT_FOUND = 2007
    private val ERROR_CODE_SOMETHING_GOES_WRONG = 1001

    private val networkErrorMapper = NetworkErrorMapper()
    private val loading = BehaviorSubject.create<Boolean>()
    private val errors = PublishSubject.create<ApiError>()

    val isLoading: Boolean
        get() = loading.hasValue() && loading.value

    constructor(requestFunction: Func0<Observable<T>>, requestPath: String) : this(requestFunction, Cache<T>(), requestPath) {}

    fun refresh() {
        requestFunction.call()
                .take(1)
                .doOnSubscribe { loading.onNext(true) }
                .doOnNext { it -> loading.onNext(false) }
                .doOnNext(Action1<T> { this.checkIfHasError(it) })
                .doOnError { it -> loading.onNext(false) }
                .doOnError { it -> errors.onNext(networkErrorMapper.mapError(it)) }
                .onErrorResumeNext { it -> empty() }
                .subscribe(Action1<T> { cache.write(it) })
    }

    private fun checkIfHasError(model: T) {
        checkForError(model.errors)
        checkForError(model.exception)
    }

    private fun checkForError(errors: List<Error>?) {
        if (null != errors && !errors.isEmpty()) {
            if (errors[0].code == ERROR_CODE_USER_NOT_FOUND || errors[0].code == ERROR_CODE_SOMETHING_GOES_WRONG) {

                throw InvalidSessionError()
            } else {
                throw ApiError(errors[0].code, errors[0].message!!)
            }
        }
    }

    fun observeLoading(): Observable<Boolean> {
        return loading
    }

    fun observeData(): rx.Observable<T> {
        if (!cache.hasData() && !isLoading) {
            refresh()
        }
        return cache.observe()
    }

    fun observeErrors(): Observable<ApiError> {
        return errors
    }
}
