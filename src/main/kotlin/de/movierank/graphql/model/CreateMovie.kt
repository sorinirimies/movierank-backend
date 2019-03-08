package de.movierank.graphql.model


fun CreateMovie.toMovie() = Movie(
    vote_count = this.vote_count,
    id = this.id,
    video = this.video,
    vote_average = this.vote_average,
    title = this.title,
    popularity = this.popularity,
    poster_path = this.poster_path,
    original_language = this.original_language,
    original_title = this.original_title,
    genre_ids = this.genre_ids,
    backdrop_path = this.backdrop_path ?: "",
    adult = this.adult,
    overview = this.overview,
    release_date = this.release_date
)

data class CreateMovie(
    var vote_count: Int = 0,
    var id: Int = 0,
    var video: Boolean = false,
    var vote_average: Double = 0.0,
    var title: String = "",
    var popularity: Double = 0.0,
    var poster_path: String? = null,
    var original_language: String = "",
    var original_title: String = "",
    var genre_ids: List<Int>? = null,
    var backdrop_path: String? = null,
    var adult: Boolean,
    var overview: String = "",
    var release_date: String = ""
)