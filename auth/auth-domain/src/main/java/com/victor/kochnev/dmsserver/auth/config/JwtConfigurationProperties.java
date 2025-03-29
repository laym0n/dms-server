package com.victor.kochnev.dmsserver.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("app.rest.security.jwt")
@Data
public class JwtConfigurationProperties {
    /**
     * Длительность жизни Jwt токена доступа
     */
    private Duration accessTokenDuration;
    /**
     * Длительность жизни Jwt токена обновления доступа
     */
    private Duration refreshTokenDuration;
    /**
     * Длительность жизни Jwt токена обновления доступа для опции remeberMe
     */
    private Duration refreshTokenRememberMeDuration;
    /**
     * Секрет для генерации Jwt токена
     */
    private String secret;
}
