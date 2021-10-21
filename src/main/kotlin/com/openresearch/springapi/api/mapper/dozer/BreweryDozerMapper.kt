package com.openresearch.springapi.api.mapper.dozer

import com.github.dozermapper.core.Mapper
import com.openresearch.springapi.model.Brewery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BreweryDozerMapper @Autowired constructor(mapper: Mapper) : AbstractDozerMapper<Brewery>(mapper)
