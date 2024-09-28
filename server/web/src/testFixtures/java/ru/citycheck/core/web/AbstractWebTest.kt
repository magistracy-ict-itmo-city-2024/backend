package ru.citycheck.core.web

import ru.citycheck.core.application.AbstractApplicationTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ContextConfiguration(
    classes = [WebTestConfiguration::class],
)
@TestPropertySource(locations = ["classpath:test-web.properties"])
abstract class AbstractWebTest : AbstractApplicationTest()
