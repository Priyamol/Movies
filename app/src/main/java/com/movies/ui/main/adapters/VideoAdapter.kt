package com.movies.ui.main.adapters

import com.movies.R
import com.movies.data.models.MovieVideo
import com.movies.ui.base.interfaces.OnVideoClickListener
import com.movies.ui.main.viewholders.VideoViewHolder
import com.movies.utils.Helpers.buildYouTubeThumbnailURL
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

class VideoAdapter(private var movieVideoList: List<MovieVideo>, listener: OnVideoClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var onVideoClickListener: OnVideoClickListener

    init {
        this.onVideoClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        context = parent.context

        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.trailer_single_item, parent, false)
        return VideoViewHolder(view,context, movieVideoList,onVideoClickListener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val videoViewHolder = holder as VideoViewHolder
        val movieVideo: MovieVideo = movieVideoList.get(holder.adapterPosition)


            Glide.with(context).load(buildYouTubeThumbnailURL(movieVideo.key!!)).thumbnail(0.05f)
                    .transition(withCrossFade()).into(videoViewHolder.mVideoImage)


    }

    override fun getItemCount(): Int = movieVideoList.size
}