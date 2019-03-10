package de.movierank.graphql.db

import de.movierank.graphql.model.Cast
import de.movierank.graphql.model.CastItem
import de.movierank.graphql.model.Movie
import de.movierank.graphql.model.Movies
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MoviesRepository : MoviesDao {

    override fun getCast(movieId: Int): List<CastItem> = transaction {
        Cast.select { Cast.movieId.eq(movieId) }.map { it.toCastItem() }
    }

    override fun getMovies(size: Int): List<Movie> = transaction {
        Movies.selectAll()
            .limit(size)
            .map { it.toMovieItem() }
    }

    override fun addCast(cast: CastItem, castMovieId: Int): CastItem {
        transaction {
            Cast.insert {
                it[cast_id] = cast.cast_id
                it[name] = cast.name
                it[movieId] = castMovieId
                it[id] = cast.id
                it[profile_path] = cast.profile_path ?: ""
            }
        }
        return cast
    }

    override fun getPopularMoviesByYear(year: Int) = transaction {
        Movies.selectAll()
            .map { it.toMovieItem() }
    }

    override fun getMovie(title: String): Movie? = transaction {
        Movies.select {
            Movies.title.eq(title)
        }.mapNotNull {
            it.toMovieItem()
        }
    }.singleOrNull()

    override fun addMovie(movie: Movie): Movie {
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
        return movie
    }

    private fun ResultRow.toMovieItem() = Movie(
        vote_count = this[Movies.vote_count],
        id = this[Movies.id],
        video = this[Movies.video],
        vote_average = this[Movies.vote_average],
        title = this[Movies.title],
        popularity = this[Movies.popularity],
        poster_path = this[Movies.poster_path],
        original_language = this[Movies.original_language],
        original_title = this[Movies.original_title],
        backdrop_path = this[Movies.backdrop_path],
        adult = this[Movies.adult],
        overview = this[Movies.overview],
        release_date = this[Movies.release_date]
    )

    private fun ResultRow.toCastItem() = CastItem(
        id = this[Cast.id],
        cast_id = this[Cast.cast_id],
        name = this[Cast.name],
        character = this[Cast.character],
        order = this[Cast.order],
        profile_path = this[Cast.profile_path],
        movieId = this[Cast.movieId]
    )

}