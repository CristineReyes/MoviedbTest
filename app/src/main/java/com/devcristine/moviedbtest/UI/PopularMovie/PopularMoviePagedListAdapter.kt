package com.devcristine.moviedbtest.UI.PopularMovie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devcristine.moviedbtest.Data.API.POSTER_BASE_URL
import com.devcristine.moviedbtest.Data.Repository.NetworkSate
import com.devcristine.moviedbtest.Data.VO.Movie
import com.devcristine.moviedbtest.R
import com.devcristine.moviedbtest.UI.Details.DetailOne
import kotlinx.android.synthetic.main.movie_item_list.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*


/**
 * Created by Cristine R.M. on 28/09/2021.
 * DevCristineAguirre
 */
class PopularMoviePagedListAdapter (public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffClaback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2
    private var networkSate: NetworkSate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layotInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layotInflater.inflate(R.layout.movie_item_list, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layotInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkSateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkSateItemViewHolder).bind(networkSate)
        }
    }


     fun hasExtraRow(): Boolean {
        return networkSate != null && networkSate != NetworkSate.LOADED

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount -1) {
            NETWORK_VIEW_TYPE
        }else{
            MOVIE_VIEW_TYPE
        }
    }






    class MovieDiffClaback: DiffUtil.ItemCallback<Movie>(){

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return  oldItem == newItem

        }

    }

    class MovieItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(movie: Movie?, context: Context){
            itemView.cv_movie_title.text= movie?.title
            itemView.cv_movie_release_date.text = movie?.releaseDate


            val movieImageUrl = POSTER_BASE_URL + movie?.posterPath

            Glide.with(itemView.context)
                .load(movieImageUrl)
                .into(itemView.cv_iv_ImageMovie)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailOne::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }

        }
    }

    class NetworkSateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkSate: NetworkSate?) {

            if (networkSate != null && networkSate == NetworkSate.LOADING) {
                itemView.progressBar_item.visibility = View.VISIBLE
            } else {
                itemView.progressBar_item.visibility = View.GONE

            }


            if (networkSate != null && networkSate == NetworkSate.ERROR) {
                itemView.tvErrorItem.visibility = View.VISIBLE
                itemView.tvErrorItem.text = networkSate.msg
            } else if (networkSate != null && networkSate == NetworkSate.LIST_END) {
                itemView.tvErrorItem.visibility = View.VISIBLE
                itemView.tvErrorItem.text = networkSate!!.msg

            } else {
                itemView.tvErrorItem.visibility = View.GONE
            }

        }
    }


        fun setNetworkState(newNetworkSate: NetworkSate){
            val prevState : NetworkSate? =this.networkSate
            val hadExtraRow :  Boolean = hasExtraRow()
            this.networkSate = newNetworkSate
            val hasExtraRow : Boolean = hasExtraRow()

            if(hadExtraRow != hasExtraRow){
                if(hasExtraRow){
                    notifyItemRemoved(super.getItemCount())
                }else{
                    notifyItemInserted(super.getItemCount())
                }

            }else if(hasExtraRow && prevState != networkSate){
                notifyItemInserted(itemCount-1)
            }
        }

    }




