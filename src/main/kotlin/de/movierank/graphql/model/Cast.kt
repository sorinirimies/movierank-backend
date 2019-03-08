package de.movierank.graphql.model

import org.jetbrains.exposed.sql.Table


object Cast: Table(){
    val name = varchar("name", 300)
    val cast_id = integer("cast_id")
    val character = varchar("character", 300)
    val id = integer("id")
    val movieId = integer("movieId")
    val order = integer("order")
    val profile_path = varchar("profile_path", 400)
}