package com.openresearch.springapi.api.mapper.dozer

import com.github.dozermapper.core.Mapper

@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractDozerMapper constructor(
    protected val mapper: Mapper,
) {

    fun <E, D> mapTo(entity: E, dtoClass: Class<D>): D {
        return mapper.map(entity, dtoClass)
    }

}
