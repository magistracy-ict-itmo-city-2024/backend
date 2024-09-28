package ru.citycheck.core.application

import ru.citycheck.core.domain.DomainConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan
@Import(DomainConfiguration::class)
@EnableAutoConfiguration
class ApplicationConfiguration
