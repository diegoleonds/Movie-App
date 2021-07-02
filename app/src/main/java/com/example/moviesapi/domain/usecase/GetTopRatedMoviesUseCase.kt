package com.example.moviesapi.domain.usecase

import com.example.moviesapi.data.model.Result
import com.example.moviesapi.data.repository.MovieRepositoryImpl
import com.example.moviesapi.domain.transform.MovieTransform
import com.example.moviesapi.ui.model.Movie

/**
 * get top rated movies, sorting them, and set the top one as it
 */
class GetTopRatedMoviesUseCase(
    val repository: MovieRepositoryImpl,
    val transform: MovieTransform
) {
    suspend fun getMovies(page: Int = 1): Result<ArrayList<Movie>> {
        val movies = repository.getMovies(page)
        val viewMovies = ArrayList<Movie>()
        return when (movies) {
            is Result.Success -> {
                movies.data.forEach { movie ->
                    viewMovies.add(transform.transformModelMovieIntoViewMovie(movie))
                }
                if (viewMovies.isNotEmpty()) {
                    viewMovies.sortByDescending { it.rating }
                    viewMovies[0].topRated = true
                }
                Result.Success(viewMovies)
            }
            is Result.Error -> Result.Error(movies.error)
        }
    }
}