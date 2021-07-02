package com.example.moviesapi.domain.usecase

import com.example.moviesapi.data.model.Result
import com.example.moviesapi.data.repository.MovieRepositoryImpl
import com.example.moviesapi.domain.transform.MovieTransform
import com.example.moviesapi.ui.model.Movie

/**
 * Search a movie from the api
 */
class SearchMoviesUseCase(
    val repository: MovieRepositoryImpl,
    val transform: MovieTransform
) {
    suspend fun searchMovie(query: String, page: Int = 1): Result<ArrayList<Movie>> {
        val movies = repository.searchMovie(query, page)
        val viewMovies = ArrayList<Movie>()

        return when (movies) {
            is Result.Success -> {
                movies.data.forEach {
                    viewMovies.add(transform.transformModelMovieIntoViewMovie(it))
                }
                Result.Success(viewMovies)
            }
            is Result.Error -> Result.Error(movies.error)
        }
    }
}