package org.alga.handlers

import org.alga.daos.PublicationDao
import org.alga.dtos.PublicationDto
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun ViewAllPublications() =
    "/all" bind GET to run {
        val responseLens = Body.auto<List<PublicationDao>>().toLens()

        ServerFilters.CatchLensFailure()
            .then {
                Response(OK).with(responseLens of PublicationDto.findAll())
            }
    }