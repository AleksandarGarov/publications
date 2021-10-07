package org.alga.handlers

import org.alga.daos.LecturerDao
import org.alga.dtos.LecturerDto
import org.http4k.core.*
import org.http4k.core.Method.PUT
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun AddLecturer() =
    "" bind PUT to run {
        val requestLens = Body.auto<LecturerDao>().toLens()
        val responseLens = Body.auto<LecturerDao>().toLens()

        CatchLensFailure()
            .then { req ->
                val newLecturer = requestLens(req)
                Response(OK).with(responseLens of LecturerDto.save(newLecturer))
            }
    }