package de.movierank.graphql.db

import de.movierank.graphql.model.Movie

interface MoviesDao {
    fun getMovies(): List<Movie>
    fun getMovie(title: String): Movie?
    fun addMovie(movie: Movie)
}