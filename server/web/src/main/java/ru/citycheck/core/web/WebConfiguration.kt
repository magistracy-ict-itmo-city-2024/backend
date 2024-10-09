package ru.citycheck.core.web

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.citycheck.core.application.ApplicationConfiguration
import java.util.List


@Configuration
@ComponentScan
@Import(ApplicationConfiguration::class)
@EnableAutoConfiguration
class WebConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val serverMain = Server()
        serverMain.url = "https://city24.civiltechgroup.ru"
        serverMain.description = "Main server"

        val serverLocal = Server()
        serverLocal.setUrl("http://localhost:8080")
        serverLocal.description = "Local server"
        return OpenAPI().servers(listOf(serverMain, serverLocal))
    }
}
