package com.springboot.booking.config;

import com.springboot.booking.model.ERole;
import com.springboot.booking.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()

                .authorizeHttpRequests()
                .requestMatchers(
                        "/rest/api/v1/**",
                        "/socket/**"
//                        "/v1/api/auth/**",
//                        "/v1/api/user/**",
//                        "/v1/api/admin/**",
//                        "/swagger-ui.html"
                )
                .permitAll()

//                .requestMatchers("/api/v1/admin/**").hasAnyRole(ERole.ADMIN.name())

//                .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(Permission.ADMIN_READ.name())
//                .requestMatchers(POST, "/api/v1/admin/**")
//                .hasAnyAuthority(Permission.ADMIN_CREATE.name())
//                .requestMatchers(PUT, "/api/v1/admin/**")
//                .hasAnyAuthority(Permission.ADMIN_UPDATE.name())
//                .requestMatchers(DELETE, "/api/v1/admin/**")
//                .hasAnyAuthority(Permission.ADMIN_DELETE.name())

                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout()
//                .logoutUrl("/api/v1/auth/logout")
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler(
//                        (request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
