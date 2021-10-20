package com.movies.utils

import com.movies.API_KEY.Companion.TMDB_API_KEY

import com.movies.utils.Urls.Companion.MOVIE_DETAILS_BASE_URL
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.util.*

object Helpers {



    fun buildImageUrl(path: String): String {
        return "http://image.tmdb.org/t/p/w342" + path
    }

    fun buildBackdropImageUrl(path: String): String {
        return "http://image.tmdb.org/t/p/w780" + path
    }

    fun buildYouTubeThumbnailURL(key: String): String {
        return "https://img.youtube.com/vi/$key/0.jpg"
    }

    fun buildYoutubeURL(key: String): String {
        return "https://www.youtube.com/watch?v=" + key
    }







    fun setUpTransparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }



}
