package com.example.moviesapi.domain.repository

import com.example.moviesapi.data.model.Movie
import com.example.moviesapi.data.model.Result

interface MovieRepository {
    suspend fun getMovies(page: Int): Result<ArrayList<Movie>>
    suspend fun searchMovie(query: String, page: Int): Result<ArrayList<Movie>>
}