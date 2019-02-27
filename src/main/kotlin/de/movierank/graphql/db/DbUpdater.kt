package de.movierank.graphql.db

enum class ChangeType { CREATE, UPDATE, DELETE }

data class DbUpdater<T>(val type: ChangeType, val id: Int, val entity: T)