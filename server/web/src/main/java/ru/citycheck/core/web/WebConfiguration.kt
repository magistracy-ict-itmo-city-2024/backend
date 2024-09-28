package ru.citycheck.core.web

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.citycheck.core.application.ApplicationConfiguration

@Configuration
@ComponentScan
@Import(ApplicationConfiguration::class)
@EnableAutoConfiguration
class WebConfiguration
