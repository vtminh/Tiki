package vn.tiki.android.androidhometest.mvp.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {

        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = verticalSpaceHeight
            outRect.right = verticalSpaceHeight / 2
        } else {
            outRect.left = verticalSpaceHeight / 2
            outRect.right = verticalSpaceHeight
        }

        outRect.bottom = verticalSpaceHeight

    }
}