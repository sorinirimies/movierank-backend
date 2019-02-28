package de.movierank.graphql.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.movierank.graphql.model.Movies
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

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
