package ru.citycheck.core.standalone

import ru.citycheck.core.web.WebTestConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan(
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = [StandaloneConfiguration::class],
        ),
    ],
)
@Import(WebTestConfiguration::class)
@EnableAutoConfiguration
class StandaloneTestConfiguration
