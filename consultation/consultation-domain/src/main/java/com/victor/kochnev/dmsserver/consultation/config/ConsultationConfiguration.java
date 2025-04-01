package com.victor.kochnev.dmsserver.consultation.config;

import com.victor.kochnev.dmsserver.consultation.ConsultationScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ConsultationScanMarker.class)
public class ConsultationConfiguration {
}
