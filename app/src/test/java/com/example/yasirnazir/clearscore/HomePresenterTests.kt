package com.example.yasirnazir.clearscore

import com.example.yasirnazir.clearscore.features.home.HomePresenter
import com.example.yasirnazir.clearscore.models.ApiError
import com.example.yasirnazir.clearscore.models.CreditReportInfo
import com.example.yasirnazir.clearscore.models.Response
import com.example.yasirnazir.clearscore.networking.NetworkService

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import rx.Observable

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Created by yasirnazir on 3/18/18.
 */

class HomePresenterTests {
    private val view = mock(HomePresenter.View::class.java)
    private val networkService = mock(NetworkService::class.java)
    private val presenter = HomePresenter(networkService)
    internal var response1 = Response(CreditReportInfo(514, 0, 700))
    internal var response2 = Response(CreditReportInfo(250, 0, 700))
    internal var errorNoConnection = ApiError(1000, NO_CONNECTION_ERROR)
    internal var httpError = ApiError(10001, DEFAULT_ERROR_MESSAGE)

    @Before
    fun setUp() {

    }

    @Test
    fun requestSucceedsToFetchCreditValues() {

        `when`(networkService.creditValues).thenReturn(Observable.just(response1))
        presenter.attach(view)

        verify(view).showLoading(true)
        verify<HomePresenter.View>(view).showLoading(false)
        verify<HomePresenter.View>(view).displayScore(514, 0, 700)
    }

    @Ignore
    @Test
    fun requestFailsWhenNoInternetConnection() {
        `when`(networkService.creditValues).thenReturn(Observable.error(errorNoConnection))
        presenter.attach(view)

        verify<HomePresenter.View>(view).showLoading(true)
        verify<HomePresenter.View>(view).showLoading(false)
        verify(view).showError(errorNoConnection)
    }

    @Test
    fun showErrorWhenHttpException() {
        `when`(networkService.creditValues).thenReturn(Observable.error(httpError))
        presenter.attach(view)

        verify<HomePresenter.View>(view).showLoading(true)
        verify<HomePresenter.View>(view).showLoading(false)
        verify<HomePresenter.View>(view).showError(httpError)
    }

    companion object {
        val NO_CONNECTION_ERROR = "No Internet Connection!"
        val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
    }
}
