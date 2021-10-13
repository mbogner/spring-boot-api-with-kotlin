package com.openresearch.springapi.repo.base

import com.openresearch.springapi.model.base.Identifiable
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import java.io.Serializable
import javax.persistence.EntityManager

class ExtendedJpaRepositoryImpl<ID : Serializable, T : Identifiable<ID>> constructor(
    domainClass: JpaMetamodelEntityInformation<T, ID>,
    private val em: EntityManager
) : SimpleJpaRepository<T, ID>(domainClass, em),
    ExtendedJpaRepository<ID, T> {

    override fun clear() {
        em.clear()
    }

    override fun getEntityManager(): EntityManager {
        return em
    }

    override fun refresh(entity: T) {
        em.refresh(entity)
    }
}
