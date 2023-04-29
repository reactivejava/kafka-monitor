package io.dope.kafka.monitor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/**").permitAll()
                .and()
                .httpBasic().disable();

        return http.build();
    }
}
