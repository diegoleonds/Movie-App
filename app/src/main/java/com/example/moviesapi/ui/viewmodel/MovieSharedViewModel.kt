package com.example.moviesapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapi.data.model.Result
import com.example.moviesapi.domain.usecase.GetTopRatedMoviesUseCase
import com.example.moviesapi.ui.model.Movie
import org.koin.java.KoinJavaComponent.inject

/**
 * A shared viewmodel to store data that is used in more than one fragment.
 * Call it by activityViewModel scope
 */
class MovieSharedViewModel() : ViewModel() {
    private val getTopRatedMoviesUseCase by inject(GetTopRatedMoviesUseCase::class.java)

    private val _movies = MutableLiveData<Result<ArrayList<Movie>>>()
    val movies: LiveData<Result<ArrayList<Movie>>>
        get() = _movies

    suspend fun getTopRatedMovies() {
        _movies.postValue(getTopRatedMoviesUseCase.getMovies())
    }
}