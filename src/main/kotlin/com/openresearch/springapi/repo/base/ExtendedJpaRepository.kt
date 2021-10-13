package com.openresearch.springapi.repo.base

import com.openresearch.springapi.model.base.Identifiable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable
import javax.persistence.EntityManager

@NoRepositoryBean
interface ExtendedJpaRepository<ID : Serializable, T : Identifiable<ID>> : JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

    fun clear()
    fun getEntityManager(): EntityManager
    fun refresh(entity: T)
}
