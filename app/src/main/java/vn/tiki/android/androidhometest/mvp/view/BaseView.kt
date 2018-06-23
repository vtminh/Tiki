package vn.tiki.android.androidhometest.mvp.view

interface BaseView<T> {
    fun setPresenter(presenter: T)
}