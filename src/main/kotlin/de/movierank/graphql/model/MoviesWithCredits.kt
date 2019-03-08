package de.movierank.graphql.model

data class MovieWithCredits(
    override val vote_count: Int,
    override val id: Int,
    override val video: Boolean,
    override val vote_average: Double,
    override val title: String,
    override val popularity: Double,
    override val poster_path: String?,
    override val original_language: String,
    override val original_title: String,
    override val backdrop_path: String?,
    override val adult: Boolean,
    override val overview: String,
    override val release_date: String,
    val cast: List<CastItem>,
    val crew: List<CrewItem>
) : MovieBase
