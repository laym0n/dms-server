package com.victor.kochnev.dmsserver.infra.data.entity;

import com.victor.kochnev.dmsserver.auth.model.UserRole;
import com.victor.kochnev.dmsserver.infra.data.entity.converter.UserRoleCollectionConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserEntity extends BaseEntity {
    @Column(unique = true, name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(nullable = false, name = "user_roles")
    @Convert(converter = UserRoleCollectionConverter.class)
    private Collection<UserRole> roles;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;
}
