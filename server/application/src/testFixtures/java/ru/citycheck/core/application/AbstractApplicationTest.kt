package ru.citycheck.core.application

import ru.citycheck.core.domain.AbstractDomainTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ContextConfiguration(
    classes = [ApplicationTestConfiguration::class],
)
@TestPropertySource(locations = ["classpath:test-application.properties"])
abstract class AbstractApplicationTest : AbstractDomainTest()
