package com.victor.kochnev.dmsserver.profile.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileFiltersDto {
    private List<UUID> userIds;
}
