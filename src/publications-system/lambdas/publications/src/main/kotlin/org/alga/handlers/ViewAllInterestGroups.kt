package org.alga.handlers

import org.alga.daos.InterestGroupDao
import org.alga.dtos.InterestGroupDto
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun ViewAllInterestGroups() =
    "/all" bind GET to run {
        val responseLens = Body.auto<List<InterestGroupDao>>().toLens()

        ServerFilters.CatchLensFailure()
            .then {
                Response(OK).with(responseLens of InterestGroupDto.findAll())
            }
    }