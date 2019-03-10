package de.movierank.graphql.util

import com.google.gson.Gson
import de.movierank.graphql.graphql.MovierankSchema

val gsonProvider: Gson by lazy(LazyThreadSafetyMode.NONE) {
    Gson().newBuilder()
        .setDateFormat("dd.mm.yyyy")
        .create()
}
val movieRankSchema by lazy(LazyThreadSafetyMode.NONE) {
    MovierankSchema(moviesRepositoryProvider, tmbdServiceProvider)
}
