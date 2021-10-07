package org.alga.dtos

import org.alga.daos.InterestGroupDao
import org.alga.models.InterestGroup
import org.alga.utils.HibernateUtil

class InterestGroupDto {
    companion object {
        private val em = HibernateUtil.getEntityManager()
        fun save(interestGroupDao: InterestGroupDao): InterestGroupDao = run {
            val transaction = em.transaction
            transaction.begin()
            val newInterestGroup = InterestGroup(title = interestGroupDao.title)
            em.persist(newInterestGroup)
            em.flush()
            transaction.commit()
            InterestGroupDao(
                newInterestGroup.id,
                newInterestGroup.title,
                newInterestGroup.lecturers.joinToString(",") { that -> that.firstName + " " + that.lastName }
            )
        }

        fun update(interestGroupDao: InterestGroupDao): InterestGroupDao = run {
            val transaction = em.transaction
            transaction.begin()
            val interestGroupToUpdate = em.find(InterestGroup::class.java, interestGroupDao.id)
            interestGroupToUpdate.title = interestGroupDao.title
            em.persist(interestGroupToUpdate)
            em.flush()
            transaction.commit()
            InterestGroupDao(
                interestGroupToUpdate.id,
                interestGroupToUpdate.title,
                interestGroupToUpdate.lecturers.joinToString(",") { that -> that.firstName + " " + that.lastName }
            )
        }

        fun delete(interestGroupDao: InterestGroupDao): InterestGroupDao = run {
            val transaction = em.transaction
            transaction.begin()
            em.remove(em.find(InterestGroup::class.java, interestGroupDao.id))
            transaction.commit()
            interestGroupDao
        }

        fun findAll(): MutableList<InterestGroupDao> = run {
            em.createQuery(
                "select interestGroup from InterestGroup interestGroup",
                InterestGroup::class.java
            ).resultList.map {
                InterestGroupDao(
                    it.id,
                    it.title,
                    it.lecturers.joinToString(",") { that -> that.firstName + " " + that.lastName }
                )
            }.toMutableList()
        }


        fun findAllByTitles(titles: List<String>): MutableList<InterestGroup> = run {
            em.createQuery(
                "select interestGroup from InterestGroup interestGroup where interestGroup.title in :titles",
                InterestGroup::class.java
            )
                .setParameter("titles", titles)
                .resultList
        }
    }
}