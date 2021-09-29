package com.devcristine.moviedbtest.Data.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.VO.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */
class MovieDetailNtwDataSource (private  val apiService: MovieDB, private val compositeDisposable: CompositeDisposable) {

    private  val _netWorkState = MutableLiveData<NetworkSate>()

    val networkSate : LiveData<NetworkSate>
    get() = _netWorkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadredMovieResponse : LiveData<MovieDetails>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){
        _netWorkState.postValue(NetworkSate.LOADING)


        try {
            compositeDisposable.add(
                apiService.getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _netWorkState.postValue(NetworkSate.LOADED)
                        },
                        {
                            _netWorkState.postValue(NetworkSate.ERROR)
                            Log.e("DEtalle de pelicula", it.message!!)
                        }

                    )
            )


        }catch (e: Exception){
                Log.e("DEtalle de pelicula", e.message!!)

        }

    }

}

private fun <T> MutableLiveData<T>.postValue(it: MovieDB?) {

}
