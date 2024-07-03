package com.bignerdranch.android.fuck3.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fuck3.R
import com.bignerdranch.android.fuck3.ui.OnMovieClick
import com.bignerdranch.android.fuck3.ui.models.Movie

class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView = view.findViewById(R.id.movie)
        val movieRating: TextView = view.findViewById(R.id.rating)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val imdbLink = movies[position].imdb_url
                    listener?.movieClick(imdbLink)
                }
            }
        }
    }

    private var listener : OnMovieClick ?= null
    private var moviesSort : List<Movie> = movies.toList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies.sortedByDescending { it.rating }[position]

        if (movie.rating < 7) {
            holder.movieRating.setTextColor(Color.RED)
        } else {
            holder.movieRating.setTextColor(Color.GREEN)
        }

        holder.movieName.text = movie.movie
        holder.movieRating.text = movie.rating.toString()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    moviesSort
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    moviesSort.filter {
                        it.movie.toLowerCase().contains(filterPattern)
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movies = results?.values as List<Movie>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        this.movies = newMovies
        moviesSort = ArrayList(newMovies)
        notifyDataSetChanged()
    }

    fun setOnMovieClick(listener: OnMovieClick){
        this.listener = listener
    }

}