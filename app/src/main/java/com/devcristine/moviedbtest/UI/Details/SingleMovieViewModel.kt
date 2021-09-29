package com.devcristine.moviedbtest.UI.Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.devcristine.moviedbtest.Data.VO.MovieDetails
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */
class SingleMovieViewModel (private val movieRepository: MovieDetailsRepository, movieId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy{
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId) }



    val networkSate : LiveData<NetworkSate> by lazy{
        movieRepository.getMovieDetailNtwState() }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}