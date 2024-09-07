package com.practicum.mymovies.data.converters

import com.practicum.mymovies.data.dto.moviesCast.ActorResponse
import com.practicum.mymovies.data.dto.moviesCast.CastItemResponse
import com.practicum.mymovies.data.dto.moviesCast.DirectorsResponse
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastResponse
import com.practicum.mymovies.data.dto.moviesCast.OtherResponse
import com.practicum.mymovies.data.dto.moviesCast.WritersResponse
import com.practicum.mymovies.domain.models.MovieCast
import com.practicum.mymovies.domain.models.MovieCastPerson

class MoviesCastConverter {

    fun convert(response: MoviesFullCastResponse): MovieCast {
        return with(response) {
            MovieCast(
                imdbId = this.imDbId,
                fullTitle = this.fullTitle,
                directors = convertDirectors(this.directors),
                others = convertOthers(this.others),
                writers = convertWriters(this.writers),
                actors = convertActors(this.actors)
            )
        }
    }

    private fun convertDirectors(directorsResponse: DirectorsResponse): List<MovieCastPerson> {
        return directorsResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertOthers(othersResponses: List<OtherResponse>): List<MovieCastPerson> {
        return othersResponses.flatMap { otherResponse ->
            otherResponse.items.map { it.toMovieCastPerson(jobPrefix = otherResponse.job) }
        }
    }

    private fun convertWriters(writersResponse: WritersResponse): List<MovieCastPerson> {
        return writersResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertActors(actorsResponses: List<ActorResponse>): List<MovieCastPerson> {
        return actorsResponses.map { actor ->
            MovieCastPerson(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image,
            )
        }
    }

    private fun CastItemResponse.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            id = this.id,
            name = this.name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${this.description}",
            image = null,
        )
    }

}