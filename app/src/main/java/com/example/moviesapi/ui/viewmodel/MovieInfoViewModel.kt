package com.example.moviesapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapi.ui.model.Movie

class MovieInfoViewModel(): ViewModel() {
    private val _movies = MutableLiveData<ArrayList<Movie>>()
    val movies: LiveData<ArrayList<Movie>>
        get() = _movies

    /**
     * set _movies value as the list without the movie
     *
     * @param movie desired to cut of from the list
     * @param movies movies list
     */
    fun getMoviesCopyWithoutAnItem(movie: Movie, movies: ArrayList<Movie>) {
        val moviesCopy = ArrayList<Movie>()

        movies.forEach {
            moviesCopy.add(it.copy())
        }
        moviesCopy.remove(movie)
        _movies.value = moviesCopy
    }
}