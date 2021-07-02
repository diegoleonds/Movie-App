package com.example.moviesapi.data.repository

import com.example.moviesapi.data.api.MovieApi
import com.example.moviesapi.data.error.GeneralErrorHandlerImpl
import com.example.moviesapi.data.model.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.example.moviesapi.data.model.Result
import org.junit.Before
import java.lang.Exception
import retrofit2.Response as RetrofitResponse

class MovieRepositoryImplTest {
    val sucessException = "Should return a failure, not a success"
    val failureException = "Should return a success, not a failure"

    val retrofitResponse = mockk<RetrofitResponse<Response>>()
    val retrofitMovieResponse = mockk<RetrofitResponse<Movie?>>()
    val retrofitMovieResponse2 = mockk<RetrofitResponse<Movie?>>()


    val api = mockk<MovieApi>()
    val errorHandler = GeneralErrorHandlerImpl()
    val repository = MovieRepositoryImpl(api, errorHandler)
    val genres = ArrayList<Genre>(
        listOf(
            Genre(
                id = 1,
                name = "Horror"
            ),
            Genre(
                id = 2,
                name = "Action"
            )
        )
    )
    val modelGenres = ArrayList<Genre>(
        listOf(
            Genre(
                id = 1,
                name = "Horror"
            )
        )
    )
    val movie = Movie(
        id = 1,
        genres = modelGenres,
        original_title = "Shrek 4",
        overview = "Shrek 4 movie",
        runtime = 70,
        release_date = "2022-09-21",
        vote_average = 10.0,
        popularity = 10000.0,
        poster_path = "path"
    )
    val movie2 = movie.copy(id = 2, original_title = "Pilot 2")

    val movies = ArrayList<Movie>(
        listOf(
            movie,
            movie2
        )
    )

    val moviesReponse = ArrayList<MovieResponse>(
        listOf(
            MovieResponse(
                id = 1,
                genre_ids = ArrayList<Long>(emptyList())
            ),
            MovieResponse(
                id = 2,
                genre_ids = ArrayList<Long>(emptyList())
            )
        )
    )

    val response = Response(
        page = 1,
        results = moviesReponse
    )

    @Before
    fun initMockks() {
        coEvery { api.getMovieById(1) } returns retrofitMovieResponse
        coEvery { api.getMovieById(2) } returns retrofitMovieResponse2

        every { retrofitMovieResponse.body() } returns movie
        every { retrofitMovieResponse2.body() } returns movie2
    }

    @Test
    fun shouldReturnFailureWhenGetMoviesCatchAnException() = runBlocking {
        coEvery { api.getPopularMovies() } returns retrofitResponse
        every { retrofitResponse.body() } returns null

        val result = repository.getMovies(1)
        when (result) {
            is Result.Success -> throw Exception(sucessException)
            is Result.Error -> assertEquals(ErrorEntity.Unknown, result.error)
        }
    }

    @Test
    fun shouldReturnSuccessWhenGetMoviesNotCatchAnException() = runBlocking {
        coEvery { api.getPopularMovies() } returns retrofitResponse
        every { retrofitResponse.body() } returns response

        val result = repository.getMovies(1)
        when (result) {
            is Result.Success -> assertEquals(movies, result.data)
            is Result.Error -> throw Exception(failureException)
        }
    }

    @Test
    fun shouldReturnFailureWhenSearchMovieCatchAnException() = runBlocking {
        coEvery { api.searchMovie(any(), any()) } returns retrofitResponse
        every { retrofitResponse.body() } returns null

        val result = repository.searchMovie("query", 1)
        when (result) {
            is Result.Success -> throw Exception(sucessException)
            is Result.Error -> assertEquals(ErrorEntity.Unknown, result.error)
        }
    }

    @Test
    fun shouldReturnSuccessWhenSearchMovieNotCatchAnException() = runBlocking {
        coEvery { api.searchMovie(any(), any()) } returns retrofitResponse
        every { retrofitResponse.body() } returns response

        val result = repository.searchMovie("query", 1)
        when (result) {
            is Result.Success -> assertEquals(movies, result.data)
            is Result.Error -> throw Exception(failureException)
        }
    }

    @Test
    fun shouldReturnFailureWhenGetMovieByIdCatchAnException() = runBlocking {
        coEvery { api.getMovieById(any()) } throws Exception("This is an fake exception used to " +
                "repository.getMoviesByMoviesResponseId(moviesReponse) return an error")

        val result = repository.getMoviesByMoviesResponseId(moviesReponse)
        when (result) {
            is Result.Success -> throw Exception(sucessException)
            is Result.Error -> assertEquals(ErrorEntity.Unknown, result.error)
        }
    }

    @Test
    fun shouldReturnSuccessWhenGetMovieByIdNotCatchAnException() = runBlocking {
        coEvery { api.searchMovie(any(), any()) } returns retrofitResponse
        every { retrofitResponse.body() } returns response

        val result = repository.searchMovie("query", 1)
        when (result) {
            is Result.Success -> assertEquals(movies, result.data)
            is Result.Error -> throw Exception(failureException)
        }
    }
}