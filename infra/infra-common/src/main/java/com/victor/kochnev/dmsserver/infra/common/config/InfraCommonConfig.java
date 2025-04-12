package com.victor.kochnev.dmsserver.infra.common.config;

import com.victor.kochnev.dmsserver.infra.common.InfraCommonScanMarker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan(basePackageClasses = InfraCommonScanMarker.class)
@Order()
public class InfraCommonConfig {

    @Bean
    public SecurityFilterChain denyAllSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().denyAll()
                ).build();
    }
}
