package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.auth.infra.UserModelRepository;
import com.victor.kochnev.dmsserver.common.exception.ModuleException;
import com.victor.kochnev.dmsserver.common.exception.ResourceAlreadyExistsException;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationSlotModelRepository;
import com.victor.kochnev.dmsserver.consultation.infra.WorkingTimeModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.profile.model.DoctorProfileModel;
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
        var workingTimes = workingTimeModelRepository.findAllByUserId(consultation.getDoctor().getId());
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

        var optionalExistedConsultation = consultationModelRepository.findByDoctorIdAndStartDateTime(consultation.getDoctor().getId(), consultation.getStartDateTime());
        if(optionalExistedConsultation.isPresent()) {
            throw new ResourceAlreadyExistsException(ConsultationModel.class);
        }

        var currentUserId = securityUserService.getCurrentUser().getId();

        var patientUserModel = userModelRepository.getById(currentUserId);
        var consultationSlot = consultationSlotModelRepository.getById(consultation.getConsultationSlot().getId());

        consultation.setPatient(patientUserModel);
        consultation.setDoctor(consultationSlot.getUser());
        consultation.setConsultationSlot(consultationSlot);
        return consultationModelRepository.create(consultation);
    }

    @Override
    public List<DoctorProfileModel> findConsultationSlots(ConsultationSlotsFilterDto filterDto) {
        return List.of();
    }

    @Override
    public List<ConsultationModel> getCurrentUserConsultations() {
        var currentUserId = securityUserService.getCurrentUser().getId();
        return consultationModelRepository.findByPatientIdOrDoctorId(currentUserId, currentUserId);
    }
}
