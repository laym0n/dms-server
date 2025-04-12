package com.victor.kochnev.dmsserver.infra.api.config;

import com.victor.kochnev.dmsserver.infra.common.security.JwtAuthenticationFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilterBuilder jwtAuthenticationFilterBuilder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain restPresentersSecurityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {
        var expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(context);

        var filter = jwtAuthenticationFilterBuilder.build();

        return http
                .securityMatcher("/profile/**", "/authentication/**", "/consultation/**", "/autocomplete/**", "/consultationslot/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication/**", "/autocomplete/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/profile/**", "/consultationslot/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/profile/**", "/consultation/**", "/consultationslot/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile/*", "/consultationslot/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile").authenticated()
                        .requestMatchers("/consultation/**").authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true);
    }
}
