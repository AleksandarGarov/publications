package org.alga.utils

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class HibernateUtil {
    companion object {
        @JvmStatic
        fun getSessionFactory(): EntityManagerFactory =
            Persistence.createEntityManagerFactory("org.alga.publications")

        @JvmStatic
        fun getEntityManager(): EntityManager = getSessionFactory().createEntityManager()

        @JvmStatic
        fun closeEntityManager() = getEntityManager().close()
    }
}