package com.example.moviesapi.data.model

import java.time.LocalDate
import kotlin.collections.ArrayList
/**
 * Just a wrapper for api calls, in domain layer is transformed in a model from view layer,
 * like a POJO
 */
data class Movie(
        val id: Long,
        val genres: ArrayList<Genre>,
        val original_title: String,
        val overview: String,
        val runtime: Int,
        val release_date: String,
        val vote_average: Double,
        val popularity: Double,
        val poster_path: String
) {
}