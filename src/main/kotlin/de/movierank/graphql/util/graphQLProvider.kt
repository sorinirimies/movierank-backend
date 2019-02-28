package de.movierank.graphql.util

import com.google.gson.Gson
import de.movierank.graphql.db.MoviesDbService
import de.movierank.graphql.graphql.MovierankSchema
import de.movierank.graphql.model.MovieResults
import de.movierank.graphql.model.Movies
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

val gsonProvider by lazy(LazyThreadSafetyMode.NONE) { Gson() }
val movieRankSchema by lazy(LazyThreadSafetyMode.NONE) {
    MovierankSchema((MoviesDbService()))
}
