package de.movierank.graphql.graphql

import com.github.pgutkowski.kgraphql.KGraphQL
import de.movierank.graphql.db.MoviesDao
import de.movierank.graphql.model.Movie

class MovierankSchema(moviesDao: MoviesDao) {
    val schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("movie") {
            description = "Returns a movie item based on the queried name"
            resolver { name: String -> moviesDao.getMovie(name) }
        }
        query("movies") {
            description = "Returns the entire list of movies"
            resolver { -> moviesDao.getMovies() }
        }

        type<Movie>()
    }
}