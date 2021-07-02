package com.example.moviesapi.domain.transform

import com.example.moviesapi.data.model.Genre
import com.example.moviesapi.data.model.Movie as ModelMovie
import com.example.moviesapi.ui.model.Movie as ViewMovie
import org.junit.Assert.*
import org.junit.Test

class MovieTransformTest {
    val transform = MovieTransform()

    val viewGenre = "Horror"
    val modelGenres = ArrayList<Genre>(
        listOf(
            Genre(
                id = 1,
                name = viewGenre
            )
        )
    )
    val modelMovie = ModelMovie(
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
    val viewMovie = ViewMovie(
        id = 1,
        genres = viewGenre,
        title = modelMovie.original_title,
        overview = modelMovie.overview,
        runtime = "1h 10m",
        releaseYear = "2022",
        rating = 5,
        popularity = modelMovie.popularity,
        movieImgUrl = modelMovie.poster_path
    )

    @Test
    fun shouldTransformModelMovieIntoOneEqualsToViewMovie() {
        assertEquals(viewMovie, transform.transformModelMovieIntoViewMovie(modelMovie))
    }

    @Test
    fun shouldTransformModelMovieIntoOneDifferentFromViewMovie() {
        assertNotEquals(
            viewMovie,
            transform.transformModelMovieIntoViewMovie(modelMovie.copy(id = 2))
        )
    }

    @Test
    fun convertTimeTest() {
        assertEquals("1h 10m", transform.transformMinutesIntoHours(70))
        assertEquals("1h", transform.transformMinutesIntoHours(60))
        assertEquals("1h 01m", transform.transformMinutesIntoHours(61))
        assertEquals("0h 59m", transform.transformMinutesIntoHours(59))
        assertEquals("0h 09m", transform.transformMinutesIntoHours(9))
    }

    @Test
    fun getOnlyYearFromDateTest() {
        assertEquals("2022", transform.getYearFromDate("2022-02-01"))
    }

    @Test
    fun convertRatingBaseTest() {
        assertEquals(5, transform.transformRating10BaseInto5Base(10.0))
        assertEquals(4, transform.transformRating10BaseInto5Base(9.0))
        assertEquals(4, transform.transformRating10BaseInto5Base(8.0))
        assertEquals(3, transform.transformRating10BaseInto5Base(7.0))
        assertEquals(3, transform.transformRating10BaseInto5Base(6.0))
        assertEquals(2, transform.transformRating10BaseInto5Base(5.0))
        assertEquals(2, transform.transformRating10BaseInto5Base(4.0))
        assertEquals(1, transform.transformRating10BaseInto5Base(3.0))
        assertEquals(1, transform.transformRating10BaseInto5Base(2.0))
        assertEquals(0, transform.transformRating10BaseInto5Base(1.0))
    }

    @Test
    fun convertModelGenreArrayIntoString() {
        val genre1Name = "Genre 1"
        val genre2Name = "Genre 2"
        val genre1 = Genre(
            1,
            genre1Name
        )
        val genre2 = Genre(
            2,
            genre2Name
        )

        assertEquals(
            "$genre1Name / $genre2Name",
            transform.transformGenreIntoString(
                ArrayList<Genre>(
                    listOf(
                        genre1, genre2
                    )
                )
            )
        )
        assertEquals("", transform.transformGenreIntoString(ArrayList()))
        assertEquals(genre1Name, transform.transformGenreIntoString(ArrayList(listOf(genre1))))
        assertEquals(genre2Name, transform.transformGenreIntoString(ArrayList(listOf(genre2))))
    }
}