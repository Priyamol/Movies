package com.movies.data.repositories

import com.movies.API_KEY.Companion.TMDB_API_KEY
import com.movies.data.models.MovieDetail
import com.movies.data.network.NetworkService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsRepository(){

    private val networkService: NetworkService = NetworkService.instance

    fun getMovieDetails(movieId: String): LiveData<MovieDetail> {

        val movieDetails: MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
        networkService.tmdbApi.getDetailMovie(movieId,TMDB_API_KEY,"videos")
                .enqueue(object : Callback<MovieDetail> {
                    override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                        Log.i("MovieDetails Error","Details and Video fetch failed")
                    }

                    override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                        movieDetails.value = response!!.body()
                    }
                })
        return movieDetails
    }

}