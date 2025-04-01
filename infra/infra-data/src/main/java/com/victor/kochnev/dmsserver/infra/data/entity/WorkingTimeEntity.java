package com.victor.kochnev.dmsserver.infra.data.entity;

import com.victor.kochnev.dmsserver.consultation.model.WorkingTimeModel;
import com.victor.kochnev.dmsserver.infra.data.entity.converter.WorkingTimeModelDayOfWeekCollectionConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;

@Entity
@Table(name = "working_time")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class WorkingTimeEntity extends BaseEntity {
    @Column(name = "days", nullable = false)
    @Convert(converter = WorkingTimeModelDayOfWeekCollectionConverter.class)
    private Collection<WorkingTimeModel.DayOfWeek> days;
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    @Column(name = "zone_id", nullable = false)
    private ZoneId zoneId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
