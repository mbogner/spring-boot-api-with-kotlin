package com.openresearch.springapi.model.base

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.UUIDGenerator
import java.io.Serializable

class AssignableUUIDGenerator : UUIDGenerator() {

    companion object {

        const val NAME = "assignable-uuid"
        const val PATH = "com.openresearch.springapi.model.base.AssignableUUIDGenerator"
    }

    @Throws(HibernateException::class)
    override fun generate(
        session: SharedSessionContractImplementor?,
        obj: Any?
    ): Serializable? {
        if (obj is Identifiable<*>) {
            @Suppress("UNCHECKED_CAST")
            val entity = obj as Identifiable<out Serializable>
            if (null != entity.getIdentifier()) {
                return entity.getIdentifier()
            }
        }
        return super.generate(session, obj)
    }

}
