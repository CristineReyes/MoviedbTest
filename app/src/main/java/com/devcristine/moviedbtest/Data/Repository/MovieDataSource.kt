package com.devcristine.moviedbtest.Data.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.API.ONE_PAGE
import com.devcristine.moviedbtest.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Cristine R.M. on 28/09/2021.
 * DevCristineAguirre
 */

// Clave de la pelicula para generar una lista de peliculas
class MovieDataSource(private val apiService: MovieDB, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int,  Movie>(){

    private var page= ONE_PAGE
    val networkSate:MutableLiveData<NetworkSate> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>) {
        networkSate.postValue(NetworkSate.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.resultsList, null, page +1)
                        networkSate.postValue(NetworkSate.LOADED)
                    },
                    {
                        networkSate.postValue(NetworkSate.ERROR)
                        Log.e("MovieData Source", it.message.toString())


                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkSate.postValue(NetworkSate.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){
                            callback.onResult(it.resultsList, params.key+1)
                            networkSate.postValue(NetworkSate.LOADED)
                        }else{
                            networkSate.postValue(NetworkSate.LIST_END)

                        }
                    },
                    {
                        networkSate.postValue(NetworkSate.ERROR)
                        Log.e("MovieData Source", it.message!!)


                    }
                )
        )
    }


}