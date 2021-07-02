package com.example.moviesapi.data.model

/**
 * Just a wrapper for api calls
 */
data class Response (
    val page: Int,
    val results: ArrayList<MovieResponse>
) {
}