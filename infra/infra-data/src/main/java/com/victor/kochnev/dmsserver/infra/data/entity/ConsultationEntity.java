package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "consultation")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ConsultationEntity extends BaseEntity {
    @Column(name = "start_date_time", nullable = false)
    private ZonedDateTime startDateTime;
    @Column(name = "consultation_result")
    private String consultationResult;
    @ManyToOne
    @JoinColumn(name = "consultation_slot_id", nullable = false)
    private ConsultationSlotEntity consultationSlot;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private UserEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;
}
