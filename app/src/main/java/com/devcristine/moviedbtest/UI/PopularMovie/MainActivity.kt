package com.devcristine.moviedbtest.UI.PopularMovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.devcristine.moviedbtest.Data.API.MovieCredential
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import com.devcristine.moviedbtest.R
import com.devcristine.moviedbtest.UI.Details.DetailOne
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepositoy : RepositoryMoviePageList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovieDB = MovieCredential.getClient()
        movieRepositoy = RepositoryMoviePageList(apiService)
        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManag = GridLayoutManager(this, 3)

        gridLayoutManag.spanSizeLookup = object  : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType : Int = movieAdapter.getItemViewType(position)

                 if(viewType ==  movieAdapter.MOVIE_VIEW_TYPE) {
                     return  1
                }else{
                     return 3
                }
            }
        }

        rv_MovieList.layoutManager = gridLayoutManag
        rv_MovieList.setHasFixedSize(true)
        rv_MovieList.adapter = movieAdapter


        viewModel.moviePageList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkSate.observe(this, Observer {
            progressBar_Popular.visibility = if (viewModel.listEmpty() && it== NetworkSate.LOADING){
                View.VISIBLE
            }else{
                View.GONE
            }

            tvError_popular.visibility = if (viewModel.listEmpty() && it== NetworkSate.ERROR){
                View.VISIBLE
            }else{
                View.GONE
            }

            if(!viewModel.listEmpty()){
                movieAdapter.setNetworkState(it)
            }

        })








    }


    private fun getViewModel():MainActivityViewModel{

        return  ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepositoy) as T
            }
        })[MainActivityViewModel::class.java]
    }
}