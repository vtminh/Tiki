package vn.tiki.android.androidhometest.mvp.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import vn.tiki.android.androidhometest.R;
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal;
import vn.tiki.android.androidhometest.util.TimeUtil;

import static vn.tiki.android.androidhometest.util.StringUtil.convertPrice;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    interface OnDataListener {
        void onEmptyData();
    }

    private String TAG = "Message";
    private Context context;

    private OnDataListener mListener;

    private List<Deal> mData = new ArrayList<>();

    private SparseArray<CountDownTimer> countDownMap = new SparseArray<>();


    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        Log.e("TAG", "size :  " + countDownMap.size());
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }


    public void replaceData(List<Deal> filmList) {

        this.mData = filmList;
        notifyDataSetChanged();

        //find out the maximum time the timer
        long maxTime = System.currentTimeMillis();
        for (Deal item : mData) {
            maxTime = Math.max(maxTime, item.getEndDate().getTime());
        }
    }

    DealAdapter(Context context, OnDataListener listener) {
        this.context = context;
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Deal deal = mData.get(position);

        Log.i(TAG, "onBindViewHolder: " + mData.size());
        holder.tvName.setText(deal.getProductName());

        double price = (deal.getProductPrice() * 1000);
        holder.tvPrice.setText(convertPrice(price, "đ"));

        Glide.with(context)
                .load(deal.getProductThumbnail())
                .into(holder.ivPoster);

        final Date current = new Date();


        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }

        long time = deal.getEndDate().getTime() - current.getTime();
        if (time > 0) {

            holder.countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    Map timeRemain = TimeUtil.computeDiff(new Date(), deal.getEndDate());
                    final String timeString = timeRemain.get(TimeUnit.HOURS) + " giờ " + timeRemain.get(TimeUnit.MINUTES) + " phút " + timeRemain.get(TimeUnit.SECONDS) + " giây";
                    holder.tvTime.setText(timeString);
                }

                public void onFinish() {
                    if (holder.getAdapterPosition() >= 0 && holder.getAdapterPosition() < mData.size()) {
                        new Handler().post(new Runnable() {
                            public void run() {
                                mData.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                    }

                }
            }.start();

            countDownMap.put(holder.tvTime.hashCode(), holder.countDownTimer);
        } else {
            if (holder.getAdapterPosition() >= 0 && holder.getAdapterPosition() < mData.size()) {
                new Handler().post(new Runnable() {
                    public void run() {
                        mData.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });

            }
        }

    }


    public Deal getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        public TextView tvTime;
        TextView tvName;
        TextView tvPrice;

        CountDownTimer countDownTimer;

        ViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }


    }
}
