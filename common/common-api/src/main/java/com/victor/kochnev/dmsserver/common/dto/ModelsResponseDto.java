package com.victor.kochnev.dmsserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelsResponseDto<E> {
    private List<E> models;
}
