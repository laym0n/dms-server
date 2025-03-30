package com.victor.kochnev.dmsserver.auth.api.auth;

import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenData {
    private UserSecurityModel user;
    private boolean isRememberMeSet;
}
