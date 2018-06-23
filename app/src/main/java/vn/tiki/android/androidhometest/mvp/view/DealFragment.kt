package vn.tiki.android.androidhometest.mvp.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal
import vn.tiki.android.androidhometest.mvp.presenter.DealContract
import vn.tiki.android.androidhometest.mvp.presenter.DealPresenter


class DealFragment : Fragment(), DealContract.View {
    internal var TAG = "TAG"
    internal lateinit var mAdapter: DealAdapter
    private var rvDeal: RecyclerView? = null
    private var tvEmpty: TextView? = null
    lateinit var mPresenter: DealContract.Presenter

    internal val NUM_COLUMN = 2

    internal var dividerHeight: Int = 0

    override val isActive: Boolean
        get() = isAdded

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        DealPresenter(this)
        mAdapter = DealAdapter(context!!, object : DealAdapter.OnDataListener {
            override fun onEmptyData() {
                showEmptyLayout()
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mView = inflater.inflate(R.layout.deal_layout, container,
                false)

        dividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                resources.displayMetrics).toInt()


        rvDeal = mView.findViewById<View>(R.id.rvList) as RecyclerView

        tvEmpty = mView.findViewById(R.id.tvEmpty)

        rvDeal!!.addOnItemTouchListener(RecyclerTouchListener(context, rvDeal,
                object : RecyclerTouchListener.ItemClickListener {
                    override fun onLongClick(view: View?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onClick(view: View, position: Int) {
                        onClickItem(position)
                    }

                }))

        setStyleType()
        setAdapter()

        mPresenter.loadDealList(false)

        return mView
    }

    internal fun onClickItem(pos: Int) {

    }

    internal fun setAdapter() {
        rvDeal!!.adapter = mAdapter

    }

    internal fun setStyleType() {
        val mLayoutManager = GridLayoutManager(context, NUM_COLUMN)
        rvDeal!!.layoutManager = mLayoutManager
        rvDeal!!.itemAnimator = DefaultItemAnimator()

        val dividerItemDecoration = GridSpaceItemDecoration(dividerHeight)
        rvDeal!!.addItemDecoration(dividerItemDecoration)
    }


    override fun showDealList(deals: List<Deal>) {
        mAdapter.replaceData(deals)
    }

    override fun showDealDetailUi(deal: Deal) {

    }

    override fun setPresenter(presenter: DealContract.Presenter) {
        mPresenter = presenter
    }

    private fun showEmptyLayout() {
        rvDeal!!.visibility = View.GONE
        tvEmpty!!.visibility = View.VISIBLE
    }
}