package com.devcristine.moviedbtest.Data.API

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */

const val BASE_URL="https://api.themoviedb.org/3/"
const val API_KEY="5735f4e411eb69fcba849696020a3be1"
const val POSTER_BASE_URL="https://image.tmdb.org/t/p/w342"
const val ONE_PAGE =1
const val POST_PER_PAGE=20


/* Query for details:
    https://api.themoviedb.org/3/movie/343611?api_key=5735f4e411eb69fcba849696020a3be1

    Access Page number  - popular
    https://api.themoviedb.org/3/movie/popular?api_key=5735f4e411eb69fcba849696020a3be1&page=1

    https://image.tmdb.org/t/p/w342/cGLL4SY6jFjjUZkz2eFxgtCtGgK.jpg
*
* */

object MovieCredential {

    fun  getClient(): MovieDB{
        val requestInerceptor = Interceptor{
            chain ->


            val url : HttpUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request : Request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request) // explicacion
        }

        val okHttpClient : OkHttpClient=OkHttpClient.Builder()
            .addInterceptor(requestInerceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()


        val retrofit : Retrofit.Builder = Retrofit.Builder()

      /*  retrofit
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDB::class.java)*/

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDB::class.java)



    }


}