package vn.tiki.android.androidhometest.mvp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

import org.json.JSONException

class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView?, private val clickListener: ItemClickListener?) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    interface ItemClickListener {
        @Throws(JSONException::class)
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView?.findChildViewUnder(e.x, e.y)
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

        val child = rv.findChildViewUnder(e.x, e.y)

        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            try {
                clickListener.onClick(child, rv.getChildAdapterPosition(child))
            } catch (e1: JSONException) {
                e1.printStackTrace()
            }


        }


        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}