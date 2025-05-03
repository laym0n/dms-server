package com.victor.kochnev.dmsserver.infra.bundlr.proxy.config;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.BundlrProxyScanMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ComponentScan(basePackageClasses = BundlrProxyScanMarker.class)
@EnableConfigurationProperties(BundlrProxyProperties.class)
@RequiredArgsConstructor
public class BundlrProxyInfraConfig {
    private final BundlrProxyProperties properties;

    @Bean
    public RestClient bundlrProxyRestClient() {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
