package com.example.moviesapi.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapi.ui.model.Movie
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import kotlin.math.exp

class MovieInfoViewModelTest {
    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    val viewModel = MovieInfoViewModel()
    val movie = Movie(
        id = 1,
        title = "Toy Story 6",
        genres = "Funny",
        overview = "Toy Story Again",
        runtime = "2h 35m",
        releaseYear = "2023",
        rating = 5,
        popularity = 10.0,
        movieImgUrl = "url"
    )
    val movie2 = movie.copy(id = 2, title = "Toy Story Live Action")
    val movie3 = movie.copy(id = 3, title = "Aliens vs Toys")
    val movies = ArrayList<Movie>(listOf(
        movie,
        movie2,
        movie3
    ))

    @Test
    fun shouldGetMoviesListWithoutMovie3() {
        val expectedMovies = ArrayList<Movie>(listOf(
            movie,
            movie2
        ))
        viewModel.getMoviesCopyWithoutAnItem(movie3, movies)

        assertEquals(expectedMovies, viewModel.movies.value)
        assertNotEquals(movies, viewModel.movies.value)
    }

    @Test
    fun shouldGetMoviesListWithAllMovies() {
        val movie4 = movie.copy(id = 4, title = "?", overview = "This is not a good Movie")
        viewModel.getMoviesCopyWithoutAnItem(movie4, movies)

        assertEquals(movies, viewModel.movies.value)
    }
}