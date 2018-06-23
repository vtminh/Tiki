package vn.tiki.android.androidhometest.mvp.presenter

import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal
import vn.tiki.android.androidhometest.mvp.view.BaseView


class DealContract {

    interface View : BaseView<Presenter> {
        val isActive: Boolean
        fun showDealList(deals: List<Deal>)
        fun showDealDetailUi(deal: Deal)


    }

    interface Presenter : BasePresenter {
        fun loadDealList(forceUpdate: Boolean)
        fun openDealDetail(deal: Deal)
    }


}
