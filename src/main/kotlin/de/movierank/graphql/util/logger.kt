package de.movierank.graphql.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun logger(id: String = "Movierank-Server: "): Logger = LoggerFactory.getLogger(id)
