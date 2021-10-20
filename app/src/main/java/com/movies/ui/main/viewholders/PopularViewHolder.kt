package com.movies.ui.main.viewholders

import com.movies.R
import com.movies.data.database.entities.PopularEntry
import com.movies.data.models.Movie
import com.movies.ui.base.interfaces.OnMovieClickListener
import com.movies.utils.Constants
import com.movies.utils.Helpers
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.bumptech.glide.load.resource.bitmap.CenterCrop

import com.bumptech.glide.request.RequestOptions




class PopularViewHolder(itemView: View?,
                           val context: Context,
                           val listener: OnMovieClickListener
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {


    private var movie: PopularEntry? = null
    var moviePoster: ImageView = itemView!!.findViewById(R.id.single_item_movie_image)

    init{

        itemView?.setOnClickListener(this)

    }

    fun bindPopularData(movie: PopularEntry?) {
        if (movie == null) {
            return
        } else {

            this.movie = movie


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(20))

            Glide.with(context).load(Helpers.buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                .apply(requestOptions)
                        .transition(DrawableTransitionOptions.withCrossFade()).into(moviePoster)
        }
    }

    private fun convertEntryToMovieList(movie: PopularEntry): Movie {
        val passMovie = Movie()
        passMovie.id = movie.movieId
        passMovie.video = movie.video
        passMovie.title = movie.title
        passMovie.popularity = movie.popularity
        passMovie.posterPath = movie.posterPath!!
        passMovie.originalLanguage = movie.originalLanguage
        passMovie.originalTitle = movie.originalTitle
        passMovie.backdropPath = movie.backdropPath!!
        passMovie.adult = movie.adult
        passMovie.overview = movie.overview
        passMovie.releaseDate = movie.releaseDate
        passMovie.contentType = Constants.CONTENT_MOVIE
        passMovie.tableName = Constants.POPULAR
        return passMovie
    }


    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!= RecyclerView.NO_POSITION){
            listener.onMovieClickListener(convertEntryToMovieList(movie!!))
        }
    }

}