package org.alga.daos

data class LecturerDao(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val publications: String? = null,
    val citations: String? = null,
    val interestGroups: String
)
