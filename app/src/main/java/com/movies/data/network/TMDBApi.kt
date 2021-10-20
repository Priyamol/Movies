package com.movies.data.network

import com.movies.API_KEY.Companion.TMDB_API_KEY
import com.movies.data.models.MovieDetail
import com.movies.data.requestmodels.MovieRequest
import com.movies.data.requestmodels.MovieVideosRequest
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val TAG: String = "TMDBApi"

interface TMDBApi {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") pageNumber: Int,
                         @Query("region") region:String): Call<MovieRequest>


    @GET("movie/{movieId}")
    fun getDetailMovie(@Path("movieId") movieId: String,
                       @Query("api_key") apiKey: String,
                       @Query("append_to_response") response: String): Call<MovieDetail>

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id") id: Long, @Query("api_key") apiKey: String): Call<MovieVideosRequest>

}



fun getPopularMovies( service: NetworkService,
                       language: String = "en-US",
                       page: Int,
                       region: String = "US",
                       onSuccess: (movierequest: MovieRequest) -> Unit,
                       onError: (error: String) -> Unit){

    service.tmdbApi.getPopularMovies(TMDB_API_KEY,language,
            page,region).enqueue(
            object : Callback<MovieRequest> {
                override fun onFailure(call: Call<MovieRequest>?, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                        call: Call<MovieRequest>?,
                        response: Response<MovieRequest>
                ) {
                    Log.d(TAG, "got a response $response")
                    if (response.isSuccessful) {
                        val movierequest = response.body() ?: MovieRequest()
                        onSuccess(movierequest)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )

}

