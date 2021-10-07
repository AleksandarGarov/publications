package org.alga.dtos

import org.alga.daos.LecturerDao
import org.alga.daos.PublicationDao
import org.alga.models.Lecturer
import org.alga.models.Publication
import org.alga.utils.HibernateUtil
import javax.persistence.EntityManager

class LecturerDto {
    companion object {
        private val em: EntityManager = HibernateUtil.getEntityManager()

        fun save(lecturerDao: LecturerDao): LecturerDao = run {
            val transaction = em.transaction
            transaction.begin()
            val newLecturer = Lecturer(
                firstName = lecturerDao.firstName,
                lastName = lecturerDao.lastName,
                interestGroups = InterestGroupDto.findAllByTitles(lecturerDao.interestGroups.split(",").toMutableList())
            )
            em.persist(newLecturer)
            em.flush()
            transaction.commit()
            LecturerDao(
                newLecturer.id,
                newLecturer.firstName,
                newLecturer.lastName,
                interestGroups = newLecturer.interestGroups.map { it.title }.joinToString(",")
            )
        }

        fun update(lecturerDao: LecturerDao): LecturerDao = run {
            val transaction = em.transaction
            transaction.begin()
            val lecturerToUpdate = em.find(Lecturer::class.java, lecturerDao.id)
            lecturerToUpdate.firstName = lecturerDao.firstName
            lecturerToUpdate.lastName = lecturerDao.lastName
            lecturerToUpdate.interestGroups =
                InterestGroupDto.findAllByTitles(lecturerDao.interestGroups.split(",").toMutableList())
            em.persist(lecturerToUpdate)
            em.flush()
            transaction.commit()
            LecturerDao(
                lecturerToUpdate.id,
                lecturerToUpdate.firstName,
                lecturerToUpdate.lastName,
                interestGroups = lecturerToUpdate.interestGroups.map { it.title }.joinToString(",")
            )
        }

        fun delete(lecturerDao: LecturerDao): LecturerDao = run {
            val transaction = em.transaction
            transaction.begin()
            em.remove(em.find(Lecturer::class.java, lecturerDao.id))
            transaction.commit()
            lecturerDao
        }

        fun findAll(): MutableList<LecturerDao> = run {
            em.createQuery("select lecturer from Lecturer lecturer", Lecturer::class.java).resultList.map {
                LecturerDao(
                    it.id!!,
                    it.firstName,
                    it.lastName,
                    it.publications.map { that -> that.title }.joinToString(","),
                    it.publicationCitations.map { that -> that.title }.joinToString(","),
                    it.interestGroups.map { that -> that.title }.joinToString(",")
                )
            }.toMutableList()
        }

        fun findById(id: Long): Lecturer = run {
            em.createQuery("select lecturer from Lecturer lecturer where lecturer.id = :id", Lecturer::class.java)
                .setParameter("id", id)
                .singleResult
        }

        fun findByFullName(fullName: String): Lecturer = run {
            val nameParts = fullName.split(" ")
            em.createQuery(
                "select lecturer from Lecturer lecturer where lecturer.firstName = :firstName and lecturer.lastName = :lastName",
                Lecturer::class.java
            )
                .setParameter("firstName", nameParts[0])
                .setParameter("lastName", nameParts[1])
                .singleResult
        }

        fun findAllById(ids: List<Long>): MutableList<Lecturer> = run {
            em.createQuery("select lecturer from Lecturer lecturer where lecturer.id in :ids", Lecturer::class.java)
                .setParameter("ids", ids)
                .resultList
        }
    }
}