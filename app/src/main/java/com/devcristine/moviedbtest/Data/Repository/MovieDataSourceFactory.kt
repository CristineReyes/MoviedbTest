package com.devcristine.moviedbtest.Data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Cristine R.M. on 28/09/2021.
 * DevCristineAguirre
 */
class MovieDataSourceFactory (private val apiService: MovieDB, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {


    val moviesListDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesListDataSource.postValue(movieDataSource)

        return movieDataSource
    }


}