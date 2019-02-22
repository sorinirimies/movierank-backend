package de.movierank.graphql

import de.movierank.graphql.util.initExposedDb
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.server.netty.EngineMain

fun Application.module() {
    install(CallLogging)
    install(DefaultHeaders)
    initExposedDb()
}

fun main(args: Array<String>): Unit = EngineMain.main(args)
