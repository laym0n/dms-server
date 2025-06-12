package com.victor.kochnev.dmsserver.infra.bundlr.proxy.config;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.BundlrProxyScanMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ComponentScan(basePackageClasses = BundlrProxyScanMarker.class)
@EnableConfigurationProperties(BundlrProxyProperties.class)
@RequiredArgsConstructor
public class BundlrProxyInfraConfig {
    private final BundlrProxyProperties properties;

    @Bean
    public RestClient bundlrProxyRestClient() {
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.of(2, ChronoUnit.MINUTES));
        requestFactory.setConnectionRequestTimeout(Duration.of(2, ChronoUnit.MINUTES));
        requestFactory.setReadTimeout(Duration.of(2, ChronoUnit.MINUTES));
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
