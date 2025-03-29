package com.victor.kochnev.dmsserver.infra.data.config;

import com.victor.kochnev.dmsserver.infra.data.entity.EntityScanMarker;
import com.victor.kochnev.dmsserver.infra.data.repository.RepositoryScanMarker;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = EntityScanMarker.class)
@EnableJpaRepositories(basePackageClasses = RepositoryScanMarker.class)
public class DataConfig {
}
