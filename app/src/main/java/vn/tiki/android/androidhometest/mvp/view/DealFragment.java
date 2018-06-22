package vn.tiki.android.androidhometest.mvp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.tiki.android.androidhometest.R;
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal;
import vn.tiki.android.androidhometest.mvp.presenter.DealContract;
import vn.tiki.android.androidhometest.mvp.presenter.DealPresenter;


public class DealFragment extends Fragment implements DealContract.View {
    String TAG = "TAG";
    DealAdapter mAdapter;
    private RecyclerView rvDeal;
    private TextView tvEmpty;
    public DealContract.Presenter mPresenter;

    final int NUM_COLUMN = 2;

    int dividerHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        new DealPresenter(this);
        mAdapter = new DealAdapter(getContext(), new DealAdapter.OnDataListener() {
            @Override
            public void onEmptyData() {
                showEmptyLayout();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.deal_layout, container,
                false);

        dividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics());


        rvDeal = (RecyclerView) mView.findViewById(R.id.rvList);

        tvEmpty = mView.findViewById(R.id.tvEmpty);

        rvDeal.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvDeal,
                new RecyclerTouchListener.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        onClickItem(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));

        setStyleType();
        setAdapter();

        mPresenter.loadDealList(false);

        return mView;
    }

    void onClickItem(int pos) {

    }

    void setAdapter() {
        rvDeal.setAdapter(mAdapter);

    }

    void setStyleType() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), NUM_COLUMN);
        rvDeal.setLayoutManager(mLayoutManager);
        rvDeal.setItemAnimator(new DefaultItemAnimator());

        GridSpaceItemDecoration dividerItemDecoration = new GridSpaceItemDecoration(dividerHeight);
        rvDeal.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void showDealList(List<Deal> deals) {
        mAdapter.replaceData(deals);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showDealDetailUi(Deal deal) {

    }

    @Override
    public void setPresenter(DealContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showEmptyLayout() {
        rvDeal.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }
}