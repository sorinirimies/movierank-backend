package de.movierank.graphql.graphql

import com.google.gson.Gson
import de.movierank.graphql.util.logger
import de.movierank.graphql.util.movieRankSchema
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post


data class GraphQLRequest(val query: String = "", val variables: Map<String, Any> = emptyMap())

fun GraphQLErrors.asMap(): Map<String, Map<String, String>> {
    return mapOf(
        "errors" to mapOf(
            "message" to "Caught ${e.javaClass.simpleName}: ${e.message?.replace("\"", "")}"
        )
    )
}

data class GraphQLErrors(val e: Exception)

fun Route.graphql(gson: Gson) {
    post("/graphql") {

        val request = call.receive<GraphQLRequest>()

        val query = request.query
        logger().info("the graphql query: $query")

        val variables = gson.toJson(request.variables)
        logger().info("the graphql variables: $variables")

        try {
            val result = movieRankSchema.schema.execute(query, variables)
            call.respondText(result)
        } catch (e: Exception) {
            call.respondText(gson.toJson(GraphQLErrors(e).asMap()))
        }
    }
}