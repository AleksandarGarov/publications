package org.alga.handlers

import org.alga.daos.PublicationDao
import org.alga.dtos.PublicationDto
import org.http4k.core.*
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun UpdatePublication() =
    "" bind POST to run {
        val requestLens = Body.auto<PublicationDao>().toLens()
        val responseLens = Body.auto<PublicationDao>().toLens()

        ServerFilters.CatchLensFailure()
            .then { req ->
                val updatePublication = requestLens(req)
                Response(OK).with(responseLens of PublicationDto.update(updatePublication))
            }
    }