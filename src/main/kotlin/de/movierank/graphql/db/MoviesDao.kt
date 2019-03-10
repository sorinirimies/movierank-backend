package de.movierank.graphql.db

import de.movierank.graphql.model.CastItem
import de.movierank.graphql.model.Movie

interface MoviesDao {
    fun getMovies(size: Int): List<Movie>
    fun addCast(cast: CastItem, castMovieId: Int): CastItem
    fun getCast(movieId: Int): List<CastItem>
    fun getPopularMoviesByYear(year: Int): List<Movie>
    fun getMovie(title: String): Movie?
    fun addMovie(movie: Movie): Movie
}