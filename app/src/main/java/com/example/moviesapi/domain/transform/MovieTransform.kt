package com.example.moviesapi.domain.transform

import com.example.moviesapi.data.model.Genre
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.collections.ArrayList
import com.example.moviesapi.ui.model.Movie as ViewMovie
import com.example.moviesapi.data.model.Movie as ModelMovie

/**
 * Transform a movie from data layer into one from ui layer
 */
class MovieTransform {
    fun transformModelMovieIntoViewMovie(modelMovie: ModelMovie): ViewMovie {
        return ViewMovie(
            id = modelMovie.id,
            title = modelMovie.original_title,
            genres = transformGenreIntoString(modelMovie.genres),
            overview = modelMovie.overview,
            runtime = transformMinutesIntoHours(modelMovie.runtime),
            rating = transformRating10BaseInto5Base(modelMovie.vote_average),
            popularity = modelMovie.popularity,
            releaseYear = getYearFromDate(modelMovie.release_date),
            movieImgUrl = modelMovie.poster_path
        )
    }

    fun transformGenreIntoString(genres: ArrayList<Genre>): String {
        var stringGenres = ""
        for (i in 0 until genres.size) {
            stringGenres += if (i < genres.size - 1) {
                "${genres[i].name} / "
            } else {
                genres[i].name
            }
        }
        return stringGenres
    }

    fun transformMinutesIntoHours(minutes: Int): String {
        val hours = minutes / 60
        val hoursMinutes = minutes % 60
        var time = hours.toString() + "h"

        if (hoursMinutes > 0) {
            if (hoursMinutes > 9) {
               time += " ${hoursMinutes}m"
            } else {
                time += " 0${hoursMinutes}m"
            }
        }
        return time
    }

    fun getYearFromDate(date: String): String {
        if (date.length > 4) {
            return date.substring(0, 4)
        }
        return date
    }

    fun transformRating10BaseInto5Base(rating: Double): Int = (rating / 2).toInt()
}