package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.consultation.infra.ConsultationFiltersDto;
import com.victor.kochnev.dmsserver.consultation.infra.ConsultationModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.infra.data.converter.ConsultationEntityMapper;
import com.victor.kochnev.dmsserver.infra.data.repository.ConsultationRepository;
import com.victor.kochnev.dmsserver.infra.data.repository.ConsultationSlotRepository;
import com.victor.kochnev.dmsserver.infra.data.repository.UserRepository;
import com.victor.kochnev.dmsserver.infra.data.specification.ConsultationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConsultationModelRepositoryImpl implements ConsultationModelRepository {
    private final UserRepository userRepository;
    private final ConsultationRepository consultationRepository;
    private final ConsultationEntityMapper consultationEntityMapper;
    private final ConsultationSlotRepository consultationSlotRepository;

    @Override
    @Transactional
    public ConsultationModel create(ConsultationModel consultation) {
        var consultationToCreate = consultationEntityMapper.mapToEntity(consultation);

        var patient = userRepository.findById(consultation.getPatient().getId()).get();
        var doctor = userRepository.findById(consultation.getDoctor().getId()).get();
        var consultationSlot = consultationSlotRepository.findById(consultation.getConsultationSlot().getId()).get();

        consultationToCreate.setPatient(patient);
        consultationToCreate.setDoctor(doctor);
        consultationToCreate.setConsultationSlot(consultationSlot);

        var createdConsultation = consultationRepository.save(consultationToCreate);
        return consultationEntityMapper.mapToModel(createdConsultation);
    }

    @Override
    @Transactional
    public List<ConsultationModel> findByPatientIdOrDoctorId(UUID patientId, UUID doctorId) {
        return consultationRepository.findByPatientIdOrDoctorId(patientId, doctorId)
                .stream()
                .map(consultationEntityMapper::mapToModel)
                .toList();
    }

    @Override
    @Transactional
    public Optional<ConsultationModel> findByDoctorIdAndStartDateTime(UUID doctorId, ZonedDateTime startDateTime) {
        return consultationRepository.findByDoctorIdAndStartDateTime(doctorId, startDateTime)
                .map(consultationEntityMapper::mapToModel);
    }

    @Override
    @Transactional
    public List<ConsultationModel> findAllByFilters(ConsultationFiltersDto consultationFilters) {
        var spec = ConsultationSpecification.byFilters(consultationFilters);
        return consultationRepository.findAll(spec)
                .stream()
                .map(consultationEntityMapper::mapToModel)
                .toList();
    }

    @Override
    public Optional<ConsultationModel> findById(UUID consultationId) {
        return consultationRepository.findById(consultationId)
                .map(consultationEntityMapper::mapToModel);
    }
}
