package com.openresearch.springapi.config

import com.openresearch.springapi.logging.logger
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.nio.charset.StandardCharsets
import java.util.*

@Configuration
class LocaleConfig {

    companion object {
        val DEFAULT_LOCALE: Locale = Locale.ENGLISH
        private val SUPPORTED_LOCALES = listOf(Locale.GERMAN, Locale.ENGLISH)

        private const val TRANSLATIONS = "classpath:locale/messages"
    }

    private val log: Logger = logger()

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.defaultLocale = DEFAULT_LOCALE
        resolver.supportedLocales = SUPPORTED_LOCALES
        log.info("set locales: default={}, supported={}", DEFAULT_LOCALE, SUPPORTED_LOCALES)
        return resolver
    }

    @Bean
    @Qualifier("messages")
    fun messageSource(
        @Value("\${application.translation.useCodeAsDefaultMessage:true}") useCodeAsDefaultMessage: Boolean
    ): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename(TRANSLATIONS)
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName())
        messageSource.setUseCodeAsDefaultMessage(useCodeAsDefaultMessage)
        log.info("using translations from {} (useCodeAsDefaultMessage={})", TRANSLATIONS, useCodeAsDefaultMessage)
        return messageSource
    }

}
