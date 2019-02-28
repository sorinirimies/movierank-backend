package de.movierank.graphql.util

import de.movierank.graphql.db.MoviesDbService
import de.movierank.graphql.graphql.MovierankSchema

/*GraphQL*/
val movieRankSchema by lazy(LazyThreadSafetyMode.NONE) {
    MovierankSchema((MoviesDbService()))
}
