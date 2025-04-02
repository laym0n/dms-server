package com.victor.kochnev.dmsserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelsRequestDto<E> {
    private E filters;
}
