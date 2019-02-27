package de.movierank.graphql.service

import de.movierank.graphql.model.Movie
import de.movierank.graphql.util.API_KEY
import de.movierank.graphql.util.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.time.LocalDate
import java.time.Year

class MoviesService(private val client: HttpClient) : MoviesApi {

    override suspend fun getMostPopular() {
        val response =
            client.get<String>(urlString = "$BASE_URL/discover/movie?sort_by=popularity.desc?&api_key=$API_KEY")
        println(response)
    }


    override suspend fun getMoviesInTheaters(releaseDate: LocalDate): List<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBestMoviesByYear(year: Year): List<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}