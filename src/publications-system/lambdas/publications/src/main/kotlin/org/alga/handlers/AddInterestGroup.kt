package org.alga.handlers

import org.alga.daos.InterestGroupDao
import org.alga.dtos.InterestGroupDto
import org.http4k.core.*
import org.http4k.core.Method.PUT
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun AddInterestGroup() =
    "" bind PUT to run {
        val requestLens = Body.auto<InterestGroupDao>().toLens()
        val responseLens = Body.auto<InterestGroupDao>().toLens()

        ServerFilters.CatchLensFailure()
            .then { req ->
                val newInterestGroup = requestLens(req)
                Response(OK).with(responseLens of InterestGroupDto.save(newInterestGroup))
            }
    }