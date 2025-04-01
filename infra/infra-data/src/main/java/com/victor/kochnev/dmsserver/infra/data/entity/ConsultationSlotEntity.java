package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@Entity
@Table(name = "consultation_slot")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ConsultationSlotEntity extends BaseEntity {
    @Column(name = "duration", nullable = false)
    private Duration duration;
    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private SpecializationEntity specialization;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
