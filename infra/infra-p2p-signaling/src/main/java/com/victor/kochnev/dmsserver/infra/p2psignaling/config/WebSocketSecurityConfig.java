package com.victor.kochnev.dmsserver.infra.p2psignaling.config;

import com.victor.kochnev.dmsserver.infra.common.security.JwtAuthenticationFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSocketSecurityConfig {
    @Autowired
    private JwtAuthenticationFilterBuilder jwtAuthenticationFilterBuilder;

    @Bean
    @Order(0)
    public SecurityFilterChain webSocketsSecurityFilterChain(HttpSecurity http) throws Exception {
        var filter = jwtAuthenticationFilterBuilder.build();

        return http
                .securityMatcher("/p2p-signaling")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/p2p-signaling").permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
