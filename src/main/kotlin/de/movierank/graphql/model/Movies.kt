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

data class Movie(
    override val vote_count: Int,
    override val id: Int,
    override val video: Boolean,
    override val vote_average: Double,
    override val title: String,
    override val popularity: Double,
    override val poster_path: String?,
    override val original_language: String,
    override val original_title: String,
    val genre_ids: List<Int>? = null,
    override val backdrop_path: String?,
    override val adult: Boolean,
    override val overview: String,
    override val release_date: String
) : MovieBase

fun Movie.toMovieWithMainActor(castItem: CastItem) = MovieWithMainActor(
    this.vote_count,
    this.id,
    this.video,
    this.vote_average,
    this.title,
    this.popularity,
    this.poster_path,
    this.original_language,
    this.original_title,
    this.backdrop_path,
    this.adult,
    this.overview,
    this.release_date,
    castItem.cast_id,
    castItem.character,
    castItem.id,
    castItem.name,
    castItem.order,
    this.id,
    castItem.profile_path
)