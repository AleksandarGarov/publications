package org.alga.handlers

import org.http4k.core.HttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

fun PublicationsApi(
): HttpHandler {

    return routes(
        "/lecturer" bind routes(
            ViewAllLecturers(),
            AddLecturer(),
            UpdateLecturer(),
            DeleteLecturer()
        ),
        "/publication" bind routes(
            ViewAllPublications(),
            AddPublication(),
            UpdatePublication(),
            DeletePublication()
        ),
        "/interest-group" bind routes(
            ViewAllInterestGroups(),
            AddInterestGroup(),
            UpdateInterestGroup(),
            DeleteInterestGroup()
        )
    )
}
