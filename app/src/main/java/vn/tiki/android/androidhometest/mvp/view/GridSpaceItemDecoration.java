package vn.tiki.android.androidhometest.mvp.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public GridSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = verticalSpaceHeight;
            outRect.right = verticalSpaceHeight/2;
        } else {
            outRect.left = verticalSpaceHeight/2;
            outRect.right = verticalSpaceHeight;
        }

        outRect.bottom = verticalSpaceHeight;

    }
}