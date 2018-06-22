package vn.tiki.android.androidhometest.mvp.presenter

import android.os.AsyncTask
import android.util.Log
import vn.tiki.android.androidhometest.mvp.model.remote.api.ApiServices
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.mvp.model.remote.api.DataCallback
import vn.tiki.android.androidhometest.mvp.model.remote.api.MockRemote

class DealPresenter(private val mDealView: DealContract.View) : DealContract.Presenter {

    companion object {
        val task = MockRemote()
    }

    init {
        mDealView.setPresenter(this)
    }

    override fun loadDealList(forceUpdate: Boolean) {
        loadFilm(forceUpdate)
    }

    override fun openDealDetail(deal: Deal) {
        mDealView.showDealDetailUi(deal)
    }


    override fun start() {}

    private fun loadFilm(forceUpdate: Boolean) {
        task.getData(object : DataCallback<List<Deal>?> {
            override fun onResult(result: List<Deal>?) {
                if (result != null && result.size > 0) {
                    // The view may not be able to handle UI updates anymore
                    if (!mDealView.isActive) {
                        Log.e("TAG", "FilmView not active")
                        return
                    }
                    mDealView.showDealList(result)
                } else {
                    if (!mDealView.isActive) {
                        return
                    }
                }
            }

        })

    }

}
