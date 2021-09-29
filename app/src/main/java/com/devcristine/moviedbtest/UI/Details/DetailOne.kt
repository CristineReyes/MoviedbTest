package com.devcristine.moviedbtest.UI.Details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devcristine.moviedbtest.Data.API.MovieCredential
import com.devcristine.moviedbtest.Data.API.MovieDB
import com.devcristine.moviedbtest.Data.API.POSTER_BASE_URL
import com.devcristine.moviedbtest.Data.VO.MovieDetails
import com.devcristine.moviedbtest.R
import kotlinx.android.synthetic.main.activity_detail_one.*
import java.util.*




//import com.devcristine.moviedbtest.

//import com.devcristine.moviedbtest.R

class DetailOne : AppCompatActivity() {


    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_one)

        val movieId : Int = intent.getIntExtra("id", 1)

        val apiService: MovieDB = MovieCredential.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        // actualizacion de la interfaz en cualquier cambio de datos
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

     /*   // Estado de la conexcion en el modelo
        viewModel.networkSate.observe(this, Observer {
            progressBar.visibility = if (it== NetworkSate.LOADING) View.VISIBLE else
                View.GONE
            textViewError.visibility = if (it == NetworkSate.ERROR) View.VISIBLE else
                View.GONE

        })*/


    }


    private fun bindUI(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text= it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + "Min"
        movie_overview.text = it.overview

        // formatear la moneda
        val formatCurrency = java.text.NumberFormat.getCurrencyInstance(Locale.UK)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        // preview movie
        val moviePosterUrl: String = POSTER_BASE_URL + it.posterPath

        Glide.with(this)
            .load(moviePosterUrl)
            .into(imageViewMovie);


    }


    //importar Repsitorio d peliculas y ID de pelicula
    private fun getViewModel(moveId:Int) : SingleMovieViewModel{

        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T{

                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository , moveId) as T
            }
        })[SingleMovieViewModel::class.java]



    }
}