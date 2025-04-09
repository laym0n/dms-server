package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.common.dto.RangeDto;
import com.victor.kochnev.dmsserver.common.exception.ModuleException;
import com.victor.kochnev.dmsserver.common.exception.ResourceAlreadyExistsException;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationSlotInfoDto;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationFiltersDto;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationSlotModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.WorkingTimeModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ConsultationFacadeImpl implements ConsultationFacade {
    private final ConsultationModelRepository consultationModelRepository;
    private final UserModelRepository userModelRepository;
    private final ConsultationSlotModelRepository consultationSlotModelRepository;
    private final WorkingTimeModelRepository workingTimeModelRepository;
    private final SecurityUserService securityUserService;

    @Override
    public ConsultationModel create(ConsultationModel consultation) {
        var consultationSlot = consultationSlotModelRepository.getById(consultation.getConsultationSlot().getId());

        var workingTimes = workingTimeModelRepository.findAllByUserId(consultationSlot.getUser().getId());
        var isDoctorWorkAtConsultationTime = workingTimes.stream()
                .anyMatch(workingTime -> {
                    ZonedDateTime consultationStartDateTime = consultation.getStartDateTime();
                    var workingStartDateTime = ZonedDateTime.of(consultationStartDateTime.toLocalDate(), workingTime.getStartTime(), workingTime.getZoneId());
                    var workingEndDateTime = ZonedDateTime.of(consultationStartDateTime.toLocalDate(), workingTime.getEndTime(), workingTime.getZoneId());
                    return !consultationStartDateTime.isBefore(workingStartDateTime) && !consultationStartDateTime.isAfter(workingEndDateTime);
                });
        if (!isDoctorWorkAtConsultationTime) {
            throw new ModuleException("Doctor does not work at the consultation time");
        }

        var optionalExistedConsultation = consultationModelRepository.findByDoctorIdAndStartDateTime(consultationSlot.getUser().getId(), consultation.getStartDateTime());
        if(optionalExistedConsultation.isPresent()) {
            throw new ResourceAlreadyExistsException(ConsultationModel.class);
        }

        var currentUserId = securityUserService.getCurrentUser().getId();

        var patientUserModel = userModelRepository.getById(currentUserId);

        consultation.setPatient(patientUserModel);
        consultation.setDoctor(consultationSlot.getUser());
        consultation.setConsultationSlot(consultationSlot);
        return consultationModelRepository.create(consultation);
    }

    @Override
    public ModelsResponseDto<ConsultationSlotModel> findConsultationSlots(ModelsRequestDto<ConsultationSlotsFilterDto> requestDto) {
        List<ConsultationSlotModel> slots = consultationSlotModelRepository.findAllByFilters(requestDto.getFilters());
        return new ModelsResponseDto<>(slots);
    }

    @Override
    public ModelsResponseDto<ConsultationModel> getCurrentUserConsultations() {
        var currentUserId = securityUserService.getCurrentUser().getId();
        var consultations = consultationModelRepository.findByPatientIdOrDoctorId(currentUserId, currentUserId);
        return new ModelsResponseDto<>(consultations);
    }

    @Override
    @Transactional
    public ConsultationSlotInfoDto getConsultationSlotInfo(UUID id, ConsultationSlotInfoParams params) {
        var consultationSlot = consultationSlotModelRepository.getById(id);
        var workingTimes = workingTimeModelRepository.findAllByUserId(consultationSlot.getUser().getId());

        var consultationFilters = new ConsultationFiltersDto();
        consultationFilters.setDoctorId(consultationSlot.getUser().getId());
        consultationFilters.setStartDateTimeRange(new RangeDto<>(ZonedDateTime.of(params.getAppointmentStartDate(), LocalTime.MIDNIGHT, workingTimes.get(0).getZoneId()), ZonedDateTime.of(params.getAppointmentEndDate().plusDays(1), LocalTime.MIDNIGHT, workingTimes.get(0).getZoneId())));
        var consultations = consultationModelRepository.findAllByFilters(consultationFilters);
        var startEndAppointments = consultations.stream()
                .collect(Collectors.toMap(ConsultationModel::getStartDateTime, consultation -> consultation.getStartDateTime().plus(consultation.getConsultationSlot().getDuration())));

        var appointmentDateTimes = workingTimes.stream()
                .flatMap(workingTime ->
                        Stream.iterate(params.getAppointmentStartDate(), date -> !date.isAfter(params.getAppointmentEndDate()), date -> date.plusDays(1))
                                .filter(date -> workingTime.getDays().contains(date.getDayOfWeek()))
                                .flatMap(date -> {
                                    var startDateTime = ZonedDateTime.of(date, workingTime.getStartTime(), workingTime.getZoneId());
                                    var endDateTime = ZonedDateTime.of(date, workingTime.getEndTime(), workingTime.getZoneId());
                                    return Stream.iterate(startDateTime, dateTime -> !dateTime.plus(consultationSlot.getDuration()).isAfter(endDateTime), dateTime -> dateTime.plus(consultationSlot.getDuration()));
                                }))
                .filter(appointmentDateTime ->
                        startEndAppointments.entrySet()
                                .stream()
                                .allMatch(entry ->
                                        (appointmentDateTime.isBefore(entry.getKey()) || !appointmentDateTime.isBefore(entry.getValue()))
                                                && (!appointmentDateTime.plus(consultationSlot.getDuration()).isAfter(entry.getKey()) || appointmentDateTime.plus(consultationSlot.getDuration()).isAfter(entry.getValue()))))
                .toList();

        var consultationInfo = new ConsultationSlotInfoDto();
        consultationInfo.setConsultationSlotModel(consultationSlot);
        consultationInfo.setAppointmentDateTimes(appointmentDateTimes);
        return consultationInfo;
    }
}
