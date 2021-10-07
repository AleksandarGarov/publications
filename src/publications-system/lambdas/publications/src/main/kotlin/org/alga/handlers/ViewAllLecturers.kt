package org.alga.handlers

import org.alga.daos.LecturerDao
import org.alga.dtos.LecturerDto
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun ViewAllLecturers() =
    "/all" bind GET to run {
        val responseLens = Body.auto<List<LecturerDao>>().toLens()

        CatchLensFailure()
            .then {
                Response(OK).with(responseLens of LecturerDto.findAll())
            }
    }