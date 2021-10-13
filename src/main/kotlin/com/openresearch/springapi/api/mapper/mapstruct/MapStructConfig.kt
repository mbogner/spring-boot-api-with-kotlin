package com.openresearch.springapi.api.mapper.mapstruct

import org.mapstruct.factory.Mappers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapStructConfig {

    @Bean
    fun exampleMapStructMapper(): ExampleMapStructMapper {
        return Mappers.getMapper(ExampleMapStructMapper::class.java)
    }

}
