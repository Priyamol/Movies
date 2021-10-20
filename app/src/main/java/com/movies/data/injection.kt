package com.movies.data

import com.movies.data.database.CacheDatabase
import com.movies.data.database.localCache.PopularLocalCache
import com.movies.data.network.NetworkService
import com.movies.data.repositories.MovieDetailsRepository
import com.movies.data.repositories.PopularRepository
import com.movies.ui.main.viewmodelfactory.ViewModelDetailFactory
import com.movies.ui.main.viewmodelfactory.ViewModelPopularFactory
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executors

object Injection {




    private fun providePopularCache(context: Context): PopularLocalCache {
        val database = CacheDatabase.getInstance(context)
        return PopularLocalCache(database.poplarDao(), Executors.newSingleThreadExecutor())
    }
    private fun providePopularRepository(context: Context): PopularRepository {
        return PopularRepository(NetworkService.instance, providePopularCache(context))
    }
    fun providePopularViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelPopularFactory(providePopularRepository(context))
    }

    fun provideMovieDetailsRepository(): ViewModelProvider.Factory{
        val movieDetailsrepo = MovieDetailsRepository()
        return ViewModelDetailFactory(movieDetailsrepo)
    }

}