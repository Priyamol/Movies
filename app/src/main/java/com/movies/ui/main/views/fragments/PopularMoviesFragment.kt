package com.movies.ui.main.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.movies.R
import com.movies.data.Injection
import com.movies.data.database.CacheDatabase
import com.movies.data.database.entities.PopularEntry
import com.movies.data.models.Movie
import com.movies.data.network.NetworkService
import com.movies.ui.base.interfaces.OnMovieClickListener
import com.movies.ui.main.adapters.PopularAdapter
import com.movies.ui.main.viewmodels.PopularViewModel
import com.movies.ui.main.views.activities.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class PopularMoviesFragment : Fragment(), OnMovieClickListener{


    private lateinit var mMainView : View
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private lateinit var mLinearLayoutManager : LinearLayoutManager
    private lateinit var emptyList: TextView

    private var region:String = "US"

    private lateinit var viewModel: PopularViewModel
    private lateinit var networkService: NetworkService
    private lateinit var mDatabase: CacheDatabase

    lateinit var mPopularAdapter: PopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_popular_movies, container, false)

        initViews()
        initRecyclerView()
        setSwipeRefreshLayoutListener()
        getPopularData(region)

        return mMainView
    }

    private fun initViews(){
        mRecyclerView = mMainView.findViewById(R.id.fragment_popular_movies_recycler_view)
        mSwipeRefreshLayout = mMainView.findViewById(R.id.fragment_popular_movies_swipe_refresh)
        emptyList = mMainView.findViewById(R.id.emptyPopularList)
        networkService = NetworkService.instance
        mDatabase = CacheDatabase.getInstance(context!!.applicationContext)
    }

    private fun initRecyclerView() {
        configureRecyclerAdapter()
        viewModel = ViewModelProviders.of(this, Injection.providePopularViewModelFactory(context!!))
            .get(PopularViewModel::class.java)

        mPopularAdapter = PopularAdapter(this)
        mRecyclerView.adapter = mPopularAdapter


        viewModel.popularMovies.observe(this, Observer<PagedList<PopularEntry>> {
            showEmptyList(it?.size == 0)
            mPopularAdapter.submitList(it!!)
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
        })
    }





    private fun setSwipeRefreshLayoutListener() {
        mSwipeRefreshLayout.setOnRefreshListener {
            refreshTable()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshTable(){
        mSwipeRefreshLayout.isEnabled = false
        runBlocking {
            withContext(Dispatchers.Default) {
                mDatabase.poplarDao().deleteAll()
            }
        }
        mSwipeRefreshLayout.isEnabled = true
        mRecyclerView.scrollToPosition(0)
        viewModel.getPopular(region)
        mPopularAdapter.submitList(null)
    }

    private fun getPopularData(region: String){


            viewModel.getPopular(region)

        mPopularAdapter.submitList(null)
        mSwipeRefreshLayout.isRefreshing = false
    }
    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        }
    }



    private fun configureRecyclerAdapter() {

        mLinearLayoutManager = LinearLayoutManager(context,  RecyclerView.VERTICAL,false )
        mRecyclerView.layoutManager = mLinearLayoutManager
    }
    override fun onMovieClickListener(movie: Movie) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra("movie",movie)
        context!!.startActivity(detailIntent)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}