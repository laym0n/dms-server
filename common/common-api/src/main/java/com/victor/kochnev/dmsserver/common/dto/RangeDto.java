package com.victor.kochnev.dmsserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RangeDto<T> {
    private T startRange;
    private T endRange;
}
