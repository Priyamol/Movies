package com.movies.data.repositories

import com.movies.data.database.databaseResults.PopularResults
import com.movies.data.database.localCache.PopularLocalCache
import com.movies.data.network.NetworkService
import com.movies.ui.base.boundaryCallbacks.PopularBoundaryCallbacks
import androidx.paging.LivePagedListBuilder


class PopularRepository(
    private val service: NetworkService,
    private val popularCache: PopularLocalCache
) {

    fun popular(region: String): PopularResults {

        val dataSourceFactory = popularCache.getAllPopular()

        val boundaryCallback = PopularBoundaryCallbacks(region, service, popularCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return PopularResults(data, networkErrors)
    }



    companion object {

        private const val DATABASE_PAGE_SIZE = 1
    }

}