package com.movies.ui.main.viewholders

import com.movies.R
import com.movies.data.models.MovieVideo
import com.movies.ui.base.interfaces.OnVideoClickListener
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class VideoViewHolder(itemView: View?,
                      val context: Context,
                      val movieVideoList: List<MovieVideo>,
                      val listener: OnVideoClickListener
): RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var mVideoImage: ImageView
    init{
        mVideoImage = itemView!!.findViewById(R.id.activity_detail_trailer_poster_image)
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!=RecyclerView.NO_POSITION){
            listener.onVideoClickListener(movieVideoList.get(position))
        }
    }

}