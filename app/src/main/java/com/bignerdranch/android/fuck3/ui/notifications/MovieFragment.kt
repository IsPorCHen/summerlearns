package com.bignerdranch.android.fuck3.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.fuck3.R
import com.bignerdranch.android.fuck3.databinding.FragmentMoviesBinding
import com.bignerdranch.android.fuck3.ui.IMovieApi
import com.bignerdranch.android.fuck3.ui.OnMovieClick
import com.bignerdranch.android.fuck3.ui.adapters.MovieAdapter
import com.bignerdranch.android.fuck3.ui.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFragment : Fragment(), OnMovieClick {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieApi: IMovieApi
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: MovieAdapter
    private lateinit var context: Context
    private var searchQuery: String = ""
    private val viewmodel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        context = requireContext()


        retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApi = retrofit.create(IMovieApi::class.java)

        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        binding.Search.setQuery(searchQuery, false)
        binding.Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText ?: ""
                adapter.getFilter().filter(searchQuery)
                return false
            }
        })

        fetchMovies(context, searchQuery)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context = requireContext()
        fetchMovies(context, searchQuery)
    }

    fun fetchMovies(context: Context, query: String) {
        movieApi.getMovie().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful)  {
                    val movies = response.body() ?: emptyList()

                    adapter = MovieAdapter(movies)
                    adapter.setOnMovieClick(this@MovieFragment)
                    binding.rvMovies.adapter = adapter

                    adapter.getFilter().filter(searchQuery)

                    viewmodel.query.observe(viewLifecycleOwner) { query ->
                        val filteredMovies = movies.filter {
                            it.movie.contains(query, true)
                        }
                        adapter.updateMovies(filteredMovies)
                    }

                    binding.Search.setQuery(query, false)
                } else {
                    Log.d("MoviesFragment", "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("MoviesFragment", "Request failed", t)
            }
        })
    }


    override fun movieClick(link: String) {
        val bundle = Bundle().apply {
            putString("imdb_url", link)
            putString("search_query", searchQuery)
        }
        findNavController().navigate(R.id.action_navigation_notifications_to_movieSite2, bundle)
    }
}

