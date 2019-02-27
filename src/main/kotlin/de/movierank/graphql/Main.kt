package de.movierank.graphql

import de.movierank.graphql.graphql.graphql
import de.movierank.graphql.service.MoviesService
import de.movierank.graphql.service.movies
import de.movierank.graphql.util.*
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.launch

fun Application.module() {
    install(CallLogging)
    install(DefaultHeaders)
    initExposedDb()
    install(Routing) {
        graphql(gsonProvider, movieRankSchema.schema)
        movies(MoviesService(client))
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    launch {
        loadLocalData()
    }
}

fun main(args: Array<String>) = EngineMain.main(args)
