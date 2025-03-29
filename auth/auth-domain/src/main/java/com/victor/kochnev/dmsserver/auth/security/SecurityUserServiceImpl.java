package com.victor.kochnev.dmsserver.auth.security;

import com.victor.kochnev.dmsserver.auth.converter.UserSecurityMapper;
import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.exception.ModuleException;
import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
    private final UserModelRepository userModelRepository;
    private final UserSecurityMapper userSecurityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userByLogin;
        try {
            userByLogin = userModelRepository.getByLogin(username);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return userSecurityMapper.mapToUserSecurityModel(userByLogin);
    }

    @Override
    public UserSecurityModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserSecurityModel) authentication.getPrincipal();
    }

    @Override
    public UserSecurityModel getUserFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserSecurityModel userSecurityModel) {
            return userSecurityModel;
        }
        throw new ModuleException("Can not parse Authentication " + authentication + " to " + UserSecurityModel.class.getSimpleName());
    }
}
