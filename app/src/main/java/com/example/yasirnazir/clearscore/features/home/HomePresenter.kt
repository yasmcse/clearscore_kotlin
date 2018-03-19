package com.example.yasirnazir.clearscore.features.home


import com.example.yasirnazir.clearscore.base_classes.Presenter
import com.example.yasirnazir.clearscore.features.home.fetchers.HomeFetcher
import com.example.yasirnazir.clearscore.models.ApiError
import com.example.yasirnazir.clearscore.networking.NetworkService

import rx.functions.Action1

/**
 * Created by yasirnazir on 3/17/18.
 */

class HomePresenter(networkService: NetworkService?) : Presenter<HomePresenter.View>() {

    private val homeFetcher: HomeFetcher

    init {
        homeFetcher = HomeFetcher(networkService)
    }

    override fun onViewAttached(view: HomePresenter.View) {
        super.onViewAttached(view)
        addSubscription(homeFetcher.observeLoading().subscribe(Action1<Boolean> { view.showLoading(it) }))
        addSubscription(homeFetcher.observeErrors().subscribe { it -> view.showError(it) })
        addSubscription(homeFetcher.observeData().subscribe { it ->
            view.displayScore(it.creditReportInfo?.score!!,
                    it.creditReportInfo?.minScoreValue!!,
                    it.creditReportInfo?.maxScoreValue!!)
        })
    }


    override fun onViewDetached(view: View?) {
        super.onViewDetached(view)
    }

    interface View : Presenter.View {

        fun showLoading(show: Boolean)

        fun showError(apiError: ApiError)

        fun displayScore(score: Int, minScore: Int, maxValue: Int)

    }

}
