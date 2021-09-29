package com.devcristine.moviedbtest.Data.API

import com.devcristine.moviedbtest.Data.VO.MovieDetails
import com.devcristine.moviedbtest.Data.VO.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */
interface MovieDB {

 /*   Query for details:
    https://api.themoviedb.org/3/movie/343611?api_key=5735f4e411eb69fcba849696020a3be1

    Access Page number  - popular
    https://api.themoviedb.org/3/movie/popular?api_key=5735f4e411eb69fcba849696020a3be1&page=1

    https://api.themoviedb.org/3/

*/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int): Single<MovieResponse> // detalles de la pelicula

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id")id:Int) : Single<MovieDetails> // detalles de la pelicula


}