package vn.tiki.android.androidhometest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.releaseDependencies
import vn.tiki.android.androidhometest.mvp.view.ActivityUtils
import vn.tiki.android.androidhometest.mvp.view.DealFragment

class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initDependencies(this)

    setContentView(R.layout.activity_main)

      ActivityUtils.addFragmentToActivity(supportFragmentManager, DealFragment(),R.id.dealFrag)


  }

  override fun onDestroy() {
    super.onDestroy()
    releaseDependencies()
  }
}
