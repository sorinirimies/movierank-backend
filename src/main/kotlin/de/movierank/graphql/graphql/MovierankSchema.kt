package de.movierank.graphql.graphql

import com.github.pgutkowski.kgraphql.KGraphQL
import de.movierank.graphql.db.MoviesDao
import de.movierank.graphql.model.*
import de.movierank.graphql.rest.TmdbService
import io.ktor.features.NotFoundException
import io.ktor.util.KtorExperimentalAPI

class MovierankSchema(moviesDao: MoviesDao, tmdbService: TmdbService) {
    @KtorExperimentalAPI
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
                arg<Int> { name = "size"; defaultValue = 10; description = "The number of creditsList returned" }
            }
        }

        query("popularMovies") {
            description = "Returns a list of popular movies"
            resolver { year: Int -> tmdbService.getPopularMoviesByYear(year) }.withArgs {
                arg<Int> { name = "year"; defaultValue = 2016; description = "Popular movies retrieved based on year" }
            }
        }
        query("movieCast") {
            description = "Gets the credits for a given movie id"
            resolver { movieId: Int -> tmdbService.getMovieCast(movieId) }.withArgs {
                arg<Int> { name = "movieId"; defaultValue = 332562; description = "The id of the movie" }
            }
        }
        query("moviesWithMainActors") {
            description = "A list of movies with adjacent credits"
            resolver { year: Int? -> tmdbService.getPopularMoviesWithMainActors(year ?: 2018) }.withArgs {
                arg<Int> {
                    name = "year"; defaultValue = 2018; description = "Popular movies by year with adjacent Credits"
                }
            }
        }
        mutation("createMovie") {
            description = "Adds a new movie to the database"
            resolver { input: CreateMovie -> moviesDao.addMovie(input.toMovie()) }
        }
        inputType<CreateMovie>()
        type<CreditsResult>()
        type<MovieWithMainActor>()
        type<Movie> {
            property<MovieWithMainActor?>("moviesWithMainActor") {
                resolver { movie -> tmdbService.getMovieWithMainActor(movie) }
            }
        }
        type<CrewItem>()
        type<CastItem>()
    }
}