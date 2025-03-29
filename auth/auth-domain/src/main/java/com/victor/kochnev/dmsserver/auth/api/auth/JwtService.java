package com.victor.kochnev.dmsserver.auth.api.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.kochnev.dmsserver.auth.config.JwtConfigurationProperties;
import com.victor.kochnev.dmsserver.auth.model.JwtTokenDto;
import com.victor.kochnev.dmsserver.common.security.GrantedAuthorityModel;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
class JwtService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtConfigurationProperties jwtProperties;

    @SneakyThrows
    public UserSecurityModel getUserFromToken(String token) {
        Map<String, Object> mappedUser = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        UUID id = UUID.fromString((String) mappedUser.get("id"));
        String username = (String) mappedUser.get("username");
        UserSecurityModel userSecurity;
        if (mappedUser.containsKey("authorities")) {
            boolean enabled = (boolean) mappedUser.get("enabled");
            List<GrantedAuthorityModel> authorities = objectMapper.convertValue(mappedUser.get("authorities"), new TypeReference<>() {
            });
            userSecurity = new UserSecurityModel(id, username, "", enabled, authorities);
        } else {
            userSecurity = new UserSecurityModel(id, username);
        }
        return userSecurity;
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
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", userSecurity.getId());
        userMap.put("username", userSecurity.getUsername());
        Duration lifeTime = getRefreshTokenDuration(isRememberMeSet);
        return doGenerateToken(userMap, userSecurity.getUsername(), lifeTime);
    }

    private Duration getRefreshTokenDuration(boolean isRememberMeSet) {
        return isRememberMeSet ? jwtProperties.getRefreshTokenRememberMeDuration() : jwtProperties.getRefreshTokenDuration();
    }

    @SneakyThrows
    private String generateAccessToken(UserSecurityModel userSecurity) {
        HashMap<String, Object> userMap = objectMapper.convertValue(userSecurity, new TypeReference<>() {
        });
        userMap.remove("password");
        return doGenerateToken(userMap, userSecurity.getUsername(), jwtProperties.getAccessTokenDuration());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Duration lifeTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + lifeTime.toMillis());
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }
}
