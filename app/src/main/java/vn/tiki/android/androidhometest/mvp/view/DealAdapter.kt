package vn.tiki.android.androidhometest.mvp.view

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import java.util.ArrayList
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal
import vn.tiki.android.androidhometest.util.TimeUtil

import vn.tiki.android.androidhometest.util.StringUtil.convertPrice

class DealAdapter internal constructor(private val context: Context, private val mListener: OnDataListener) : RecyclerView.Adapter<DealAdapter.ViewHolder>() {

    private val TAG = "Message"

    private var mData  = mutableListOf<Deal>()

    private val countDownMap = SparseArray<CountDownTimer>()

    internal interface OnDataListener {
        fun onEmptyData()
    }


    fun cancelAllTimers() {
        if (countDownMap == null) {
            return
        }
        Log.e("TAG", "size :  " + countDownMap.size())
        var i = 0
        val length = countDownMap.size()
        while (i < length) {
            val cdt = countDownMap.get(countDownMap.keyAt(i))
            cdt?.cancel()
            i++
        }
    }


    fun replaceData(filmList: List<Deal>) {

        this.mData = filmList.toMutableList()
        notifyDataSetChanged()

        //find out the maximum time the timer
        var maxTime = System.currentTimeMillis()
        for (item in mData) {
            maxTime = Math.max(maxTime, item.endDate.time)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.deal_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deal = mData[position]

        Log.i(TAG, "onBindViewHolder: " + mData.size)
        holder.tvName.text = deal.productName

        val price = deal.productPrice * 1000
        holder.tvPrice.text = convertPrice(price, "đ")

        Glide.with(context)
                .load(deal.productThumbnail)
                .into(holder.ivPoster)

        val current = Date()


        if (holder.countDownTimer != null) {
            holder.countDownTimer!!.cancel()
        }

        val time = deal.endDate.time - current.time
        if (time > 0) {

            holder.countDownTimer = object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val timeRemain = TimeUtil.computeDiff(Date(), deal.endDate)
                    val timeString = timeRemain[TimeUnit.HOURS].toString() + " giờ " + timeRemain[TimeUnit.MINUTES] + " phút " + timeRemain[TimeUnit.SECONDS] + " giây"
                    holder.tvTime.text = timeString
                }

                override fun onFinish() {
                    if (holder.adapterPosition >= 0 && holder.adapterPosition < mData.size) {
                        Handler().post {
                            mData.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                        }
                    }

                }
            }.start()

            countDownMap.put(holder.tvTime.hashCode(), holder.countDownTimer)
        } else {
            if (holder.adapterPosition >= 0 && holder.adapterPosition < mData.size) {
                Handler().post {
                    mData.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }

            }
        }

    }


    fun getItem(position: Int): Deal {
        return mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var ivPoster: ImageView
        var tvTime: TextView
        internal var tvName: TextView
        internal var tvPrice: TextView

        internal var countDownTimer: CountDownTimer? = null

        init {
            ivPoster = itemView.findViewById(R.id.ivPoster)
            tvTime = itemView.findViewById(R.id.tvTime)
            tvName = itemView.findViewById(R.id.tvName)
            tvPrice = itemView.findViewById(R.id.tvPrice)
        }


    }
}
