package com.openresearch.springapi.api.mapper.dozer

import com.github.dozermapper.core.Mapper

@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractDozerMapper constructor(
    protected val mapper: Mapper,
) {

    fun <E, D> mapTo(entity: E, dtoClass: Class<D>): D {
        return mapper.map(entity, dtoClass)
    }

    fun <E, D> mapToList(entities: Collection<E>, dtoClass: Class<D>): List<D> {
        return entities.map { e -> mapper.map(e, dtoClass) }
    }

}
