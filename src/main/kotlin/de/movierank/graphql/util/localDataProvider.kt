package de.movierank.graphql.util

import com.google.gson.Gson
import de.movierank.graphql.model.MovieResults
import de.movierank.graphql.model.Movies
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

val gsonProvider by lazy(LazyThreadSafetyMode.NONE) { Gson() }

fun loadLocalData() {
    val bufferedReader = File("src/main/resources/movies.json").bufferedReader()
    val data = bufferedReader.use { it.readText() }
    gsonProvider.fromJson(data, MovieResults::class.java).apply {
        results.forEach { movie ->
            transaction {
                Movies.insert {
                    it[vote_count] = movie.vote_count
                    it[id] = movie.id
                    it[video] = movie.video
                    it[vote_average] = movie.vote_average
                    it[title] = movie.title
                    it[popularity] = movie.popularity
                    it[poster_path] = movie.poster_path ?: ""
                    it[original_language] = movie.original_language
                    it[original_title] = movie.original_title
                    it[backdrop_path] = movie.backdrop_path ?: ""
                    it[adult] = movie.adult
                    it[overview] = movie.overview
                    it[release_date] = movie.release_date
                }
            }
        }
    }
}