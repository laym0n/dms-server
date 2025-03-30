package com.victor.kochnev.dmsserver.auth.api.auth.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.kochnev.dmsserver.auth.api.auth.AccessTokenData;
import com.victor.kochnev.dmsserver.auth.api.auth.RefreshTokenData;
import com.victor.kochnev.dmsserver.auth.config.JwtConfigurationProperties;
import com.victor.kochnev.dmsserver.auth.model.JwtTokenDto;
import com.victor.kochnev.dmsserver.common.security.GrantedAuthorityModel;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtConfigurationProperties jwtProperties;

    @SneakyThrows
    public RefreshTokenData getRefreshDataFromToken(String token) {
        Map<String, Object> payload = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        var refreshTokenData = objectMapper.convertValue(payload, JwtRefreshTokenData.class);

        UUID id = refreshTokenData.getId();
        String username = refreshTokenData.getUsername();
        UserSecurityModel userSecurity = new UserSecurityModel(id, username);
        return RefreshTokenData.builder()
                .user(userSecurity)
                .isRememberMeSet(refreshTokenData.isRememberMeSet())
                .build();
    }

    @SneakyThrows
    public AccessTokenData getAccessDataFromToken(String token) {
        Map<String, Object> payload = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        var accessTokenData = objectMapper.convertValue(payload, JwtAccessTokenData.class);

        UUID id = accessTokenData.getId();
        String username = accessTokenData.getUsername();
        var enabled = accessTokenData.isEnabled();
        Collection<GrantedAuthorityModel> authorities = objectMapper.convertValue(accessTokenData.getAuthorities(), new TypeReference<>() {
        });
        UserSecurityModel userSecurity = new UserSecurityModel(id, username, "", enabled, authorities);
        return AccessTokenData.builder()
                .user(userSecurity)
                .build();
    }

    public JwtTokenDto generateJwtToken(Boolean isRememberMeSet, UserSecurityModel userSecurity) {
        String accessToken = generateAccessToken(userSecurity);
        String refreshToken = generateRefreshToken(userSecurity, isRememberMeSet);

        var jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshToken(refreshToken);
        jwtTokenDto.setAccessTokenLiveDuration(jwtProperties.getAccessTokenDuration());
        jwtTokenDto.setRefreshTokenLiveDuration(getRefreshTokenDuration(isRememberMeSet));
        return jwtTokenDto;
    }

    private String generateRefreshToken(UserSecurityModel userSecurity, boolean isRememberMeSet) {
        var data = JwtRefreshTokenData.builder()
                .id(userSecurity.getId())
                .username(userSecurity.getUsername())
                .isRememberMeSet(isRememberMeSet)
                .build();
        Duration lifeTime = getRefreshTokenDuration(isRememberMeSet);
        return doGenerateToken(data, userSecurity.getUsername(), lifeTime);
    }

    private Duration getRefreshTokenDuration(boolean isRememberMeSet) {
        return isRememberMeSet ? jwtProperties.getRefreshTokenRememberMeDuration() : jwtProperties.getRefreshTokenDuration();
    }

    @SneakyThrows
    private String generateAccessToken(UserSecurityModel userSecurity) {
        List<String> authorities = userSecurity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        var accessTokenData = JwtAccessTokenData.builder()
                .id(userSecurity.getId())
                .username(userSecurity.getUsername())
                .authorities(authorities)
                .enabled(userSecurity.isEnabled())
                .build();
        return doGenerateToken(accessTokenData, userSecurity.getUsername(), jwtProperties.getAccessTokenDuration());
    }

    private String doGenerateToken(Object claims, String subject, Duration lifeTime) {
        var claimsMap = objectMapper.convertValue(claims, Map.class);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + lifeTime.toMillis());
        return Jwts.builder()
                .claims(claimsMap)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }
}
