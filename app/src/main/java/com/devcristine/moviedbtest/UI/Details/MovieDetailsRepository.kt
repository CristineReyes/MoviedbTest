package com.devcristine.moviedbtest.UI.Details

import androidx.lifecycle.LiveData
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.VO.MovieDetails
import com.devcristine.moviedbtest.Data.Repository.MovieDetailNtwDataSource
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */
class MovieDetailsRepository (private val apiService: MovieDB) {

    private lateinit var movieDetailNtwDataSource: MovieDetailNtwDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId : Int) : LiveData<MovieDetails>{

        movieDetailNtwDataSource = MovieDetailNtwDataSource(apiService,compositeDisposable)
        movieDetailNtwDataSource.fetchMovieDetails(movieId)

        return movieDetailNtwDataSource.downloadredMovieResponse

    }


    fun getMovieDetailNtwState(): LiveData<NetworkSate>{
        return movieDetailNtwDataSource.networkSate
    }
}