package de.movierank.graphql.model

data class CastItem(
    val cast_id: Int,
    val character: String,
    val id: Int,
    val name: String,
    val movieId: Int,
    val order: Int,
    val profile_path: String? = null
)