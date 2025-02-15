package de.mathes.kanban.backend.configuration

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@EnableWebSecurity
@Configuration
class SecurityConfiguration(val userService: UserService) {

    @Autowired
    private val authProvider: CustomAuthenticationProvider? = null

    @Autowired
    @Throws(Exception::class)
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .httpBasic { }
            .formLogin {
                it.disable()
            }
            .csrf { it.disable() }
            .logout { logoutConfigurer ->
                logoutConfigurer.logoutRequestMatcher(AntPathRequestMatcher("/auth/logout"))
                    .logoutSuccessHandler { request, response, authentication ->
                        response.status = HttpServletResponse.SC_OK
                    }
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("auth/login").hasAuthority("USER")
                    .requestMatchers("/api/config").permitAll()
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/api/**").hasAuthority("USER")
                    .requestMatchers("auth/**").permitAll()
                    .anyRequest().hasAuthority("USER")
            }

        return http.build()
    }

}