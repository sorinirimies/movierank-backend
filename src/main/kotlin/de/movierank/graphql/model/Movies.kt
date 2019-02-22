package de.movierank.graphql.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object Movies : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val year = date("year")
    val description = varchar("description", 800)
    val dateUpdated = long("dateUpdated")
}

data class Movie(val id: Int, val name: String, val year: Date, val description: String)