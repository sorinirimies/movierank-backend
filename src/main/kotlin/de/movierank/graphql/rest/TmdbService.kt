package de.movierank.graphql.rest

import de.movierank.graphql.logger
import de.movierank.graphql.model.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val BASE_URL_TMDB = "https://api.themoviedb.org/3"
private const val DISCOVER_ENDPOINT = "/discover"
private const val MOVIE = "/movie"
private const val CREDITS = "/credits"
private const val RELEASE_YEAR = "primary_release_year"
private const val SORT_BY = "sort_by"
private const val TV = "tv"
private const val POPULARITY = "popularity.desc"
private const val API_KEY = "api_key=b9f60672dcb1fa228e1146eb64afa9da"

class TmdbService : TmdbApi, CoroutineScope {

    private val job: Job by lazy(LazyThreadSafetyMode.NONE) { Job() }
    private val client by lazy(LazyThreadSafetyMode.NONE) {
        HttpClient(Apache) {
            install(JsonFeature) {
                serializer = GsonSerializer {
                    setPrettyPrinting()
                    serializeNulls()
                    disableHtmlEscaping()
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun getMovieCast(movieId: Int): List<CastItem> {
        val castList = arrayListOf<CastItem>()
        runBlocking {
            val creditsResult = withContext(coroutineContext) {
                client.get<CreditsResult> {
                    url("$BASE_URL_TMDB$MOVIE/$movieId$CREDITS?$API_KEY")
                }
            }
            castList.addAll(creditsResult.cast)
        }
        return castList
    }

    override fun getMovieWithMainActor(movie: Movie): MovieWithMainActor? {
        var movieWithMainActor: MovieWithMainActor? = null
        runBlocking {
            movieWithMainActor = movie.toMovieWithMainActor(getMovieCast(movie.id)[0])
        }
        return movieWithMainActor
    }

    override fun getPopularMoviesWithMainActors(year: Int): List<MovieWithMainActor> {
        val moviesWithMainActors = arrayListOf<MovieWithMainActor>()
        runBlocking {
            val moviesResult = withContext(Dispatchers.Default) {
                client.get<MoviesResult> {
                    url("$BASE_URL_TMDB$DISCOVER_ENDPOINT$MOVIE?$RELEASE_YEAR=$year&$SORT_BY=$POPULARITY&$API_KEY")
                }
            }
            moviesResult.results.forEach { movie ->
                getMovieWithMainActor(movie)?.let { movieWithMainActor ->
                    moviesWithMainActors.add(movieWithMainActor)
                }
            }
        }
        return moviesWithMainActors
    }

    override fun getPopularMoviesByYear(year: Int): List<Movie> {
        val movies: ArrayList<Movie> = arrayListOf()
        runBlocking {
            val moviesResult = withContext(coroutineContext) {
                client.get<MoviesResult> {
                    url("$BASE_URL_TMDB$DISCOVER_ENDPOINT$MOVIE?$RELEASE_YEAR=$year&$SORT_BY=$POPULARITY&$API_KEY")
                }
            }
            movies.addAll(moviesResult.results)
        }
        logger().info(movies.toString())
        return movies
    }
}