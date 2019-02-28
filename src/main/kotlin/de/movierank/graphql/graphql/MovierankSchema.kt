package de.movierank.graphql.graphql

import com.github.pgutkowski.kgraphql.KGraphQL
import de.movierank.graphql.db.MoviesDao
import de.movierank.graphql.model.Movie
import io.ktor.features.NotFoundException

class MovierankSchema(moviesDao: MoviesDao) {
    val schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("movie") {
            description = "Returns a movie item based on the queried name"
            resolver { title: String ->
                moviesDao.getMovie(title) ?: throw NotFoundException("The movie with name $name not found")
            }
        }
        query("movies") {
            description = "Returns the entire list of movies"
            resolver { size: Int? -> moviesDao.getMovies(size ?: 10) }.withArgs {
                arg<Int> { name = "size"; defaultValue = 10; description = "The number of movies returned" }
            }
        }
        type<Movie>()
    }
}