package org.alga.daos

data class PublicationDao(
    val id: Long? = null,
    val title: String,
    val text: String,
    val lecturers: String,
    val citations: String
)