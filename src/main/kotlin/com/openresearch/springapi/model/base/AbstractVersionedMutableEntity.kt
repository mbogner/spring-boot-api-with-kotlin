package com.openresearch.springapi.model.base

import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Version

@MappedSuperclass
abstract class AbstractVersionedMutableEntity<T : Serializable> : AbstractEntity<T>() {

    @Column(name = "created_at", updatable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", insertable = false)
    open var updatedAt: Instant? = null

    @Version
    @Column(name = "lock_version")
    open var lockVersion: Long? = null

    @PrePersist
    fun prePersist() {
        createdAt = Instant.now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

}
