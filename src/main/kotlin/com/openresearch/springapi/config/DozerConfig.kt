package com.openresearch.springapi.config

import com.github.dozermapper.spring.DozerBeanMapperFactoryBean
import com.openresearch.springapi.logging.logger
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class DozerConfig {

    private val log: Logger = logger()

    @Bean
    fun dozerMapper(@Value("classpath*:dozer/**/*.xml") resources: Array<Resource>): DozerBeanMapperFactoryBean {
        val factoryBean = DozerBeanMapperFactoryBean()
        factoryBean.setMappingFiles(resources)
        log.info("using resources for dozer: {}", resources)
        return factoryBean
    }

}
