package org.alga.handlers

import org.alga.daos.LecturerDao
import org.alga.dtos.LecturerDto
import org.http4k.core.*
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun UpdateLecturer() =
    "" bind POST to run {
        val requestLens = Body.auto<LecturerDao>().toLens()
        val responseLens = Body.auto<LecturerDao>().toLens()

        ServerFilters.CatchLensFailure()
            .then { req ->
                val updateLecturer = requestLens(req)
                Response(OK).with(responseLens of LecturerDto.update(updateLecturer))
            }
    }