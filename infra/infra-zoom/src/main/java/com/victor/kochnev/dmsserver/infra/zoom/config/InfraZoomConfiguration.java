package com.victor.kochnev.dmsserver.infra.zoom.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ZoomClientProperties.class)
public class InfraZoomConfiguration {
}
