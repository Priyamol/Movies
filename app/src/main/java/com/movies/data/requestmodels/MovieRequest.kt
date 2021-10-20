package com.movies.data.requestmodels

import com.movies.data.models.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieRequest {
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0
    @SerializedName("page")
    @Expose
    var page: Int = 0
    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}