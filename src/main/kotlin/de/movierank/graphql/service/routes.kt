package de.movierank.graphql.service

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.movies(moviesService: MoviesService) {

    route("/popular") {
        //TODO this is wip
        get {
            call.respond(moviesService.getMostPopular())
        }
    }
    route("/search") {
        //TODO this is wip

    }
    route("/find") {
        //TODO this is wip

    }
}