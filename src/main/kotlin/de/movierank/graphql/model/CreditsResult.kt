package de.movierank.graphql.model

data class CreditsResult(val cast: List<CastItem>, val crew: List<CrewItem>)