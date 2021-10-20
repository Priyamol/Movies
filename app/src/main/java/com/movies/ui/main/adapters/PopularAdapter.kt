package com.movies.ui.main.adapters

import com.movies.R
import com.movies.data.database.entities.PopularEntry
import com.movies.ui.base.interfaces.OnMovieClickListener
import com.movies.ui.main.viewholders.PopularViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class PopularAdapter(private val listener: OnMovieClickListener  ) : PagedListAdapter<PopularEntry,
        RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_single_item, parent, false)
                this.context = parent.context
                return PopularViewHolder(view,context,listener)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie: PopularEntry? = getItem(position)

        if (movie != null){
            val movieViewHolder = holder as PopularViewHolder
            movieViewHolder.bindPopularData(movie)

        } else{
            notifyItemRemoved(position)
        }

    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PopularEntry>() {
            override fun areItemsTheSame(oldItem: PopularEntry, newItem: PopularEntry): Boolean =
                    oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: PopularEntry, newItem: PopularEntry): Boolean =
                    oldItem == newItem
        }
    }

}


