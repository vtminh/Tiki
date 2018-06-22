package vn.tiki.android.androidhometest.mvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONException;

public  class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    public interface ItemClickListener {
        void onClick(View view, int position) throws JSONException;
        void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    private ItemClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        final View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            try {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


        }


        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}