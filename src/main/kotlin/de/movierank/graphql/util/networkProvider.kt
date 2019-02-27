package de.movierank.graphql.util

import de.movierank.graphql.db.MoviesDbService
import de.movierank.graphql.graphql.MovierankSchema
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/*GraphQL*/
val movieRankSchema by lazy(LazyThreadSafetyMode.NONE) {
    MovierankSchema((MoviesDbService()))
}

/*REST*/
const val BASE_URL = "http://api.themoviedb.org/3"
const val API_KEY = "b9f60672dcb1fa228e1146eb64afa9da"

val client by lazy(LazyThreadSafetyMode.NONE) { HttpClient(OkHttp) }