package com.business.service.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint((exchange, exception) -> Mono.error(exception))
                .jwt();

        return http.build();
    }

}
