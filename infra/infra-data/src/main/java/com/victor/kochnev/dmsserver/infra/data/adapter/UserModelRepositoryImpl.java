package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.infra.data.converter.UserEntityMapper;
import com.victor.kochnev.dmsserver.infra.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserModelRepositoryImpl implements UserModelRepository {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional
    public UserModel create(UserModel user) {
        var userEntity = userEntityMapper.mapToEntity(user);
        var savedUser = userRepository.save(userEntity);
        return userEntityMapper.mapToModel(savedUser);
    }

    @Override
    @Transactional
    public Optional<UserModel> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userEntityMapper::mapToModel);
    }

    @Override
    @Transactional
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId)
                .map(userEntityMapper::mapToModel);
    }
}
