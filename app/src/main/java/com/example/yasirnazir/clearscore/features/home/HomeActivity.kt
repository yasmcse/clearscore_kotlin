package com.example.yasirnazir.clearscore.features.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.example.yasirnazir.clearscore.R
import com.example.yasirnazir.clearscore.models.ApiError
import com.example.yasirnazir.clearscore.networking.NetworkService

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife

import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.yasirnazir.clearscore.BaseActivity


/**
 * Created by yasirnazir on 3/14/18.
 */

class HomeActivity : BaseActivity(), HomePresenter.View {
    @BindView(R.id.yourScoreTextView)
    lateinit var yourScoreTextView: TextView
    @BindView(R.id.score)
    lateinit var scoreTextView: TextView
    @BindView(R.id.maxScoreTextView)
    lateinit var maxScoreTextView: TextView
    @BindView(R.id.scoreProgressBar)
    lateinit var scoreProgressBar: ProgressBar
    @BindView(R.id.no_internet)
    lateinit var noInternet: ImageView
    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @Inject
    lateinit var networkService: NetworkService
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.dashboard)
        deps.inject(this)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        homePresenter = HomePresenter(networkService)
        homePresenter!!.attach(this)

    }


    override fun showLoading(show: Boolean) {
        progressBar!!.visibility = if (show) VISIBLE else GONE
    }

    override fun showError(apiError: ApiError) {
        showErrorMessage(apiError.errorMessage!!)
    }

    private fun showErrorMessage(message: String) {
        updateVisibilityNoConnection()
        val view = this.window.decorView.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(view.context.resources.getColor(R.color.red))
        val snackbartextView = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        snackbartextView.maxLines = 3

        snackbar.show()
    }


    override fun displayScore(score: Int, minScore: Int, maxScore: Int) {
        updateVisibilityConnectedMode()
        scoreTextView!!.text = Integer.toString(score)
        maxScoreTextView!!.text = getString(R.string.out_of) + Integer.toString(maxScore)
        scoreProgressBar!!.max = maxScore
        val animation = ObjectAnimator.ofInt(scoreProgressBar, getString(R.string.progress), minScore, score)
        animation.duration = 3000 //in milliseconds
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    private fun updateVisibilityConnectedMode() {
        yourScoreTextView!!.visibility = View.VISIBLE
        scoreProgressBar!!.visibility = View.VISIBLE
        maxScoreTextView!!.visibility = View.VISIBLE
        scoreTextView!!.visibility = View.VISIBLE
    }

    private fun updateVisibilityNoConnection() {
        yourScoreTextView!!.visibility = View.GONE
        scoreProgressBar!!.visibility = View.GONE
        maxScoreTextView!!.visibility = View.GONE
        scoreTextView!!.visibility = View.GONE
        noInternet!!.visibility = View.VISIBLE
    }
}
