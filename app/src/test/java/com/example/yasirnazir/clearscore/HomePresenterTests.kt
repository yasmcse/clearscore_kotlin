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
    val response1 = Response(CreditReportInfo(514, 0, 700))
    val someThingWentWrongError = ApiError(1001, DEFAULT_ERROR_MESSAGE)

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

    @Test
    fun requestFailsWhenSomethingWentWrong() {
        `when`(networkService.creditValues).thenReturn(Observable.error(someThingWentWrongError))
        presenter.attach(view)
        verify<HomePresenter.View>(view).showError(someThingWentWrongError);
    }


    companion object {
        val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
    }
}
