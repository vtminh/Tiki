package vn.tiki.android.androidhometest.mvp.model.remote.api

interface DataCallback<T> {
    fun onResult(data: T)
}
