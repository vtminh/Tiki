package vn.tiki.android.androidhometest.mvp.model.remote.api

import android.os.AsyncTask
import android.util.Log
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal

class MockRemote : AsyncTask<Unit, Unit, List<Deal>>(){
    val apiServices by inject<ApiServices>()
    private lateinit var mCallback: DataCallback<List<Deal>?>;

    override fun doInBackground(vararg params: Unit?): List<Deal> {
        return apiServices.getDeals()
    }

    override fun onPostExecute(result: List<Deal>?) {
        super.onPostExecute(result)
        mCallback.onResult(result)
    }

    public fun getData( callback: DataCallback<List<Deal>?>){
        mCallback = callback

        this.execute()

    }

}