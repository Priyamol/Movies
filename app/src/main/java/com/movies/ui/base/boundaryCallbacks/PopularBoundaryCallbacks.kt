package com.movies.ui.base.boundaryCallbacks

import com.movies.data.database.entities.PopularEntry
import com.movies.data.database.localCache.PopularLocalCache
import com.movies.data.network.NetworkService
import com.movies.data.network.getPopularMovies
import com.movies.utils.Constants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.movies.data.models.Movie
import java.util.*

/**
 * PopularBoundaryCallbacks is a callback class for PopularRepository in our Model
 */

class PopularBoundaryCallbacks(
    private val region: String,
    private val service: NetworkService,
    private val cache: PopularLocalCache
) : PagedList.BoundaryCallback<PopularEntry>() {


    private var lastRequestedPage =  1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false


    override fun onItemAtEndLoaded(itemAtEnd: PopularEntry) {

    }

    override fun onZeroItemsLoaded() {
        requestAndSavePopularData(region)
    }
    fun createPopularEntryList(movie: Movie, popularEntryList: MutableList<PopularEntry>) {
        val popularEntry = PopularEntry()
        popularEntry.movieId = movie.id

        popularEntry.video = movie.video
        popularEntry.title = movie.title
        popularEntry.popularity = movie.popularity
        popularEntry.posterPath = movie.posterPath
        popularEntry.originalLanguage = movie.originalLanguage
        popularEntry.originalTitle = movie.originalTitle
        popularEntry.genreIds = movie.genreString
        popularEntry.backdropPath = movie.backdropPath
        popularEntry.adult = movie.adult
        popularEntry.overview = movie.overview
        popularEntry.releaseDate = movie.releaseDate

        popularEntry.contentType = Constants.CONTENT_SIMILAR
        popularEntry.timeAdded = Date().time

        if (popularEntry.backdropPath.isNullOrEmpty()) popularEntry.backdropPath =
            Constants.RANDOM_PATH
        if (popularEntry.posterPath.isNullOrEmpty()) popularEntry.posterPath =
            Constants.RANDOM_PATH
        popularEntryList.add(popularEntry)
    }
    private fun requestAndSavePopularData(region: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        getPopularMovies(service,"en-US",
                lastRequestedPage,
                region,
            { movierequest ->
                val popularEntryList: MutableList<PopularEntry> = mutableListOf()
                if(movierequest.results!!.size>=10){
                for (i in 0 until 10) {

                    val movie = movierequest.results!![i]
                    createPopularEntryList(movie,popularEntryList)
                }
            }
                else{
                    for (i in 0 until movierequest.results!!.size) {

                        val movie = movierequest.results!![i]
                        createPopularEntryList(movie,popularEntryList)
                    }
                }
                    cache.insert(popularEntryList) {
                        isRequestInProgress = false
                    }
                }, {
            error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })

    }

}