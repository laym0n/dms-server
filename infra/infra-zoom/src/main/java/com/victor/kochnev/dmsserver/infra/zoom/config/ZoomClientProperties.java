package com.victor.kochnev.dmsserver.infra.zoom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "infra.zoom")
@Data
public class ZoomClientProperties {
    private String accountId;
    private String clientId;
    private String clientSecret;
    private String baseUrl = "https://api.zoom.us/v2";
}
