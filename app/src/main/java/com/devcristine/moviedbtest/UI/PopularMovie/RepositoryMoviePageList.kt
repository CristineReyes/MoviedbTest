package com.devcristine.moviedbtest.UI.PopularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.API.POST_PER_PAGE
import com.devcristine.moviedbtest.Data.Repository.MovieDataSource
import com.devcristine.moviedbtest.Data.Repository.MovieDataSourceFactory
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import com.devcristine.moviedbtest.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Cristine R.M. on 28/09/2021.
 * DevCristineAguirre
 */
class RepositoryMoviePageList (private val apiSErvice: MovieDB) {

     lateinit var moviePageList :  LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun searchLiveMoviePageList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{

        movieDataSourceFactory = MovieDataSourceFactory(apiSErvice, compositeDisposable)

        val config:PagedList.Config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

            moviePageList = LivePagedListBuilder(movieDataSourceFactory, config).build()
            return moviePageList
        }

    fun getNetworkState(): LiveData<NetworkSate>{
        return Transformations.switchMap<MovieDataSource, NetworkSate>(
            movieDataSourceFactory.moviesListDataSource, MovieDataSource::networkSate
        )
    }




}