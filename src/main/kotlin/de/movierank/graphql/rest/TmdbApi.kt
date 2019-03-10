package de.movierank.graphql.rest

import de.movierank.graphql.model.CastItem
import de.movierank.graphql.model.Movie
import de.movierank.graphql.model.MovieWithCredits
import de.movierank.graphql.model.MovieWithMainActor

interface TmdbApi {
    fun getPopularMoviesByYear(year: Int): List<Movie>
    fun getMovieCast(movieId: Int): List<CastItem>
    fun getMovieWithMainActor(movie: Movie): MovieWithMainActor?
    fun getPopularMoviesWithMainActors(year: Int):List<MovieWithMainActor>
}