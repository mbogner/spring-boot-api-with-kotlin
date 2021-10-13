package com.openresearch.springapi.api.mapper.dozer

import com.github.dozermapper.core.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ExampleDozerMapper @Autowired constructor(mapper: Mapper) : AbstractDozerMapper(mapper)
