package de.movierank.graphql.service

import de.movierank.graphql.model.Movie
import java.time.LocalDate
import java.time.Year

interface MoviesApi {
    suspend fun getMostPopular()
    suspend fun getMoviesInTheaters(releaseDate: LocalDate): List<Movie>
    suspend fun getBestMoviesByYear(year: Year): List<Movie>
}