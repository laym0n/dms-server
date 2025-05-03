package com.victor.kochnev.dmsserver.infra.bundlr.proxy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "infra.bundlr.proxy")
@Getter
@Setter
public class BundlrProxyProperties {
    private String baseUrl;
}
