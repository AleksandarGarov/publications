package org.alga.dtos

import org.alga.daos.PublicationDao
import org.alga.models.Publication
import org.alga.utils.HibernateUtil
import javax.persistence.EntityManager

class PublicationDto {
    companion object {
        private val em: EntityManager = HibernateUtil.getEntityManager()

        fun save(publicationDao: PublicationDao): PublicationDao = run {
            val transaction = em.transaction
            transaction.begin()
            val newPublication = Publication(
                title = publicationDao.title,
                text = publicationDao.text,
                lecturers = publicationDao.lecturers.split(",").map { LecturerDto.findByFullName(it) }.toMutableList(),
                citations = publicationDao.citations.split(",").map { LecturerDto.findByFullName(it) }.toMutableList()
            )
            em.persist(newPublication)
            em.flush()
            transaction.commit()
            PublicationDao(
                newPublication.id,
                newPublication.title,
                newPublication.text,
                newPublication.lecturers.map { that -> that.firstName + " " + that.lastName }.joinToString(","),
                newPublication.citations.map { that -> that.firstName + " " + that.lastName }.joinToString(",")
            )
        }

        fun update(publicationDao: PublicationDao): PublicationDao = run {
            val transaction = em.transaction
            transaction.begin()
            val publicationToUpdate = em.find(Publication::class.java, publicationDao.id)
            publicationToUpdate.title = publicationDao.title
            publicationToUpdate.text = publicationDao.text
            publicationToUpdate.lecturers =
                publicationDao.lecturers.split(",").map { LecturerDto.findByFullName(it) }.toMutableList()
            publicationToUpdate.citations =
                publicationDao.citations.split(",").map { LecturerDto.findByFullName(it) }.toMutableList()
            em.persist(publicationToUpdate)
            em.flush()
            transaction.commit()
            PublicationDao(
                publicationToUpdate.id,
                publicationToUpdate.title,
                publicationToUpdate.text,
                publicationToUpdate.lecturers.map { that -> that.firstName + " " + that.lastName }.joinToString(","),
                publicationToUpdate.citations.map { that -> that.firstName + " " + that.lastName }.joinToString(",")
            )
        }

        fun delete(publicationDao: PublicationDao): PublicationDao = run {
            val transaction = em.transaction
            transaction.begin()

            em.remove(em.find(Publication::class.java, publicationDao.id))
            transaction.commit()
            publicationDao
        }

        fun findAll(): MutableList<PublicationDao> = run {
            em.createQuery("select publication from Publication publication", Publication::class.java).resultList.map {
                PublicationDao(
                    it.id!!,
                    it.title,
                    it.text,
                    it.lecturers.map { that -> that.firstName + " " + that.lastName }.joinToString(","),
                    it.citations.map { that -> that.firstName + " " + that.lastName }.joinToString(",")
                )
            }.toMutableList()
        }
    }
}
