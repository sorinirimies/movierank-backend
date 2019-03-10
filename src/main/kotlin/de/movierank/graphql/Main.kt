package de.movierank.graphql

import de.movierank.graphql.graphql.graphql
import de.movierank.graphql.util.gsonProvider
import de.movierank.graphql.util.initExposedDb
import de.movierank.graphql.util.loadLocalData
import de.movierank.graphql.util.movieRankSchema
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.content.default
import io.ktor.http.content.static
import io.ktor.routing.Routing
import io.ktor.server.netty.EngineMain
import io.ktor.util.KtorExperimentalAPI
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun logger(id: String = "Movierank-Server: "): Logger = LoggerFactory.getLogger(id)

@KtorExperimentalAPI
fun Application.module() {
    install(CallLogging)
    install(DefaultHeaders)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            setDateFormat("dd.mm.yyyy")
        }
    }
    install(Routing) {
        graphql(gsonProvider, movieRankSchema.schema)
        static("/") {
            default("index.html")
        }
    }
    initExposedDb().also {
        loadLocalData(gsonProvider)
    }
    logger().info("App loaded.")
}

fun main(args: Array<String>) = EngineMain.main(args)
