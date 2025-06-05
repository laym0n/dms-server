package com.victor.kochnev.dmsserver.consultation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingData {
    private String startUrl;
    private String joinUrl;
}
