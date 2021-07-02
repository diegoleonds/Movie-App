package com.example.moviesapi.data.api

import com.example.moviesapi.data.model.Movie
import com.example.moviesapi.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response as RetrofitResponse

interface MovieApi {
    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = Api.key,
        @Query("language") language: String = Api.language
    ): RetrofitResponse<Movie?>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = Api.key,
        @Query("language") language: String = Api.language,
        @Query("page") page: Int = 1
    ): RetrofitResponse<Response>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Api.key,
        @Query("language") language: String = Api.language,
        @Query("page") page: Int = 1
    ): RetrofitResponse<Response>
}