package com.movies.ui.main.views.activities

import com.movies.R
import com.movies.ui.main.views.fragments.PopularMoviesFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar



class MoviesActivity : AppCompatActivity() {

    private lateinit var toolBar : Toolbar

    internal lateinit var popularMoviesFragment    : PopularMoviesFragment
    //internal lateinit var nowShowingMoviesFragment : NowShowingMoviesFragment


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initViews()
        setupToolBar()

    }

    override fun onBackPressed() {
        finish()
    }
    private fun initViews(){
        toolBar = findViewById(R.id.activity_movies_toolbar)
        val popularMoviesFragment = PopularMoviesFragment()

        // Get the support fragment manager instance
        val manager = supportFragmentManager

        // Begin the fragment transition using support fragment manager
        val transaction = manager.beginTransaction()

        // Replace the fragment on container
        transaction.replace(R.id.fragment_container,popularMoviesFragment)
        transaction.addToBackStack(null)

        // Finishing the transition
        transaction.commit()

    }
    private fun setupToolBar(){
        toolBar.title = "Movies"
        setSupportActionBar(toolBar)
    }





    override fun onDestroy() {
        super.onDestroy()
    }

}