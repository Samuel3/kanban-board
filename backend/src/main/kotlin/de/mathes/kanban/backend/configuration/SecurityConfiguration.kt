package de.mathes.kanban.backend.configuration

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@EnableWebSecurity
@Configuration
class SecurityConfiguration(val userService: UserService) {

    @Autowired
    private val authProvider: CustomAuthenticationProvider? = null

    @Autowired
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { }
            .csrf {
                it.disable()
            }
            .formLogin { it.disable() }
            .logout { logoutConfigurer ->
                logoutConfigurer.logoutRequestMatcher(AntPathRequestMatcher("/auth/logout"))
                    .logoutSuccessHandler { request, response, authentication ->
                        response.status = HttpServletResponse.SC_OK
                    }
            }
            .sessionManagement{ session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("auth/login").permitAll()
                    .requestMatchers("/api/config").permitAll()
                    .requestMatchers("/api/**").hasAuthority("USER")
                    .requestMatchers("/**").permitAll()
                    .anyRequest().permitAll()
            }

        return http.build()
    }

    @Autowired
    @Throws(Exception::class)
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(BCryptPasswordEncoder())
    }


    @Bean
    @Throws(Exception::class)
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java).build()
    }

}