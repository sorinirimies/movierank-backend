package de.movierank.graphql.model

import org.jetbrains.exposed.sql.Table

object Movies : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val vote_count = integer("vote_count")
    val video = bool("video")
    val vote_average = double("vote_average")
    val title = varchar("title", 255)
    val popularity = double("popularity")
    val poster_path = varchar("poster_path", 300)
    val original_language = varchar("original_language", 255)
    val original_title = varchar("original_title", 255)
    val backdrop_path = varchar("backdrop_path", 300)
    val adult = bool("adult")
    val overview = varchar("overview", 4000)
    val release_date = varchar("release_date", 300)
}

data class MovieResults(val results: List<Movie>)
data class Movie(
    val vote_count: Int,
    val id: Int,
    val video: Boolean,
    val vote_average: Double,
    val title: String,
    val popularity: Double,
    val poster_path: String?,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>? = null,
    val backdrop_path: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String
)
