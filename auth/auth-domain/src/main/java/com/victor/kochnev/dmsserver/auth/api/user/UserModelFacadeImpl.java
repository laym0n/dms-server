package com.victor.kochnev.dmsserver.auth.api.user;

import com.victor.kochnev.dmsserver.auth.api.UserModelFacade;
import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.auth.model.UserRole;
import com.victor.kochnev.dmsserver.common.exception.ModuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserModelFacadeImpl implements UserModelFacade {
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserModel signUp(UserModel user) {
        userModelRepository.findByLogin(user.getLogin())
                .ifPresent(userModel -> {
                    throw new ModuleException("User already exists");
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(UserRole.PATIENT));
        user.setEnabled(true);
        return userModelRepository.create(user);
    }
}
