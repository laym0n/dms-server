package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.common.exception.ModuleException;
import com.victor.kochnev.dmsserver.common.exception.ResourceAlreadyExistsException;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationSlotModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.WorkingTimeModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

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
                    return consultationStartDateTime.isAfter(workingStartDateTime) && consultationStartDateTime.isBefore(workingEndDateTime);
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
}
