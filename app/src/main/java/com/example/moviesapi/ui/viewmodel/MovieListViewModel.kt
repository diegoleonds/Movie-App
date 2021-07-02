package com.example.moviesapi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapi.data.model.Result
import com.example.moviesapi.domain.usecase.SearchMoviesUseCase
import com.example.moviesapi.ui.model.Movie

class MovieListViewModel(
    val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    val movies = MutableLiveData<Result<ArrayList<Movie>>>()
    val searchMovies = MutableLiveData<Result<ArrayList<Movie>>>()

    suspend fun searchMovies(query: String) {
        searchMovies.postValue(searchMoviesUseCase.searchMovie(query))
    }
}