package ru.citycheck.core.standalone.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity, jwtFilter: JwtFilter): SecurityFilterChain? {
        http
            .csrf()
            .disable()
            .httpBasic()
            .and()
            .cors()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeRequests()
            .antMatchers(
                "/api/v0/users/auth",
                "/api/v0/users/refresh",
                "/api/v0/users/register",
                "/api/v0/users/sign_in_phone",
                "/api/v0/users/sign_in_code",
                "/api/v0/issues",
                "/api/v0/issues/",
                "/api/v0/issues/**",
                "/api/v0/categories",
                "/api/v0/categories/**",
                "/swagger-ui/*",
                "/v3/api-docs",
                "/v3/api-docs/*",
            )
            .permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest()
            .authenticated()
//            .antMatchers("/**")
//            .permitAll()
//            .anyRequest()
//            .authenticated()
        return http.build()
    }
}
