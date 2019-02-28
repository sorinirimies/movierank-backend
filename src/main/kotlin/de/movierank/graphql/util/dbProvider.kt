package de.movierank.graphql.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.movierank.graphql.logger
import de.movierank.graphql.model.MovieResults
import de.movierank.graphql.model.Movies
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun initExposedDb() {
    logger().debug("Connecting to DB...")
    Database.connect(hikari).also {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Movies)
        }
    }
}

private val hikari by lazy(LazyThreadSafetyMode.NONE) {
    HikariDataSource(HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:movierank"
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })
}

/**
 * Utility function to load movie data from JSON into the DB
 */
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