package com.victor.kochnev.dmsserver.infra.data.entity;

import com.victor.kochnev.dmsserver.consultation.model.MeetingData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;

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
    @ManyToOne
    @JoinColumn(name = "consultation_slot_id", nullable = false)
    private ConsultationSlotEntity consultationSlot;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private UserEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;
    @Column(name = "meeting_data", nullable = false, columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private MeetingData meetingData;
}
