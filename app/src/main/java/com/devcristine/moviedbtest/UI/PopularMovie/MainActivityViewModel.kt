package com.devcristine.moviedbtest.UI.PopularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import com.devcristine.moviedbtest.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Cristine R.M. on 28/09/2021.
 * DevCristineAguirre
 */
class MainActivityViewModel (private val movieRepository: RepositoryMoviePageList): ViewModel() {

    private val compositeDisposable= CompositeDisposable()

    val moviePageList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.searchLiveMoviePageList(compositeDisposable)
    }


    val networkSate : LiveData<NetworkSate> by lazy {
        movieRepository.getNetworkState()
    }

    fun listEmpty():Boolean{
        return  moviePageList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }






}