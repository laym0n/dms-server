package com.victor.kochnev.dmsserver.auth.config;

import com.victor.kochnev.dmsserver.auth.converter.UserSecurityMapper;
import com.victor.kochnev.dmsserver.auth.model.UserRole;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.List;

@Configuration
@EnableConfigurationProperties(JwtConfigurationProperties.class)
public class SpringSecurityAuthConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(UserSecurityMapper mapper) {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String patientRole = mapper.mapToGrantedAuthority(UserRole.PATIENT).getAuthority();
        String doctorRole = mapper.mapToGrantedAuthority(UserRole.DOCTOR).getAuthority();
        String moderatorRole = mapper.mapToGrantedAuthority(UserRole.MODERATOR).getAuthority();
        String hierarchy = String.format("%s > %s \n %s > %s", moderatorRole, doctorRole, doctorRole, patientRole);
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(SecurityUserService securityUserService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider userAuthenticationProvider = new DaoAuthenticationProvider();
        userAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        userAuthenticationProvider.setUserDetailsService(securityUserService);
        return userAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviderList) {
        return new ProviderManager(authenticationProviderList);
    }
}
