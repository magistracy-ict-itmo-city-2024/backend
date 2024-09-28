package ru.citycheck.core.web

import ru.citycheck.core.application.ApplicationConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan
@Import(ApplicationConfiguration::class)
@EnableAutoConfiguration
class WebConfiguration
