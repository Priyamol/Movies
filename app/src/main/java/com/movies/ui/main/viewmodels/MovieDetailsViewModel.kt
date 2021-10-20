package com.movies.ui.main.viewmodels

import com.movies.data.models.MovieDetail
import com.movies.data.repositories.MovieDetailsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieDetailsViewModel(private val detailsRepo: MovieDetailsRepository): ViewModel() {

    private var movieDetail: LiveData<MovieDetail>? = null

    fun getDetails(movieId: String): LiveData<MovieDetail>{
        if (movieDetail == null){
            movieDetail = detailsRepo.getMovieDetails(movieId)
        }
        return movieDetail!!
    }
}