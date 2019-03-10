package de.movierank.graphql.util

import de.movierank.graphql.rest.TmdbService

val tmbdServiceProvider by lazy { TmdbService() }