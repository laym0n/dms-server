package com.victor.kochnev.dmsserver.configuration;

import com.victor.kochnev.dmsserver.auth.model.UserRole;
import com.victor.kochnev.dmsserver.infra.data.entity.*;
import com.victor.kochnev.dmsserver.infra.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final WorkingTimeRepository workingTimeRepository;
    private final ConsultationSlotRepository consultationSlotRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var terapevtSpecialization = specializationRepository.save(SpecializationEntity.builder()
                .name("Терапевт")
                .build());

        var admin = userRepository.save(
                UserEntity.builder()
                        .login("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(List.of(UserRole.MODERATOR))
                        .enabled(true)
                        .build()
        );

        var doctor = userRepository.save(
                UserEntity.builder()
                        .login("doctor")
                        .password(passwordEncoder.encode("doctor"))
                        .email("kocnevviktor48@gmail.com")
                        .roles(List.of(UserRole.DOCTOR))
                        .enabled(true)
                        .build()
        );

        var patient = userRepository.save(
                UserEntity.builder()
                        .login("patient")
                        .password(passwordEncoder.encode("patient"))
                        .email("victor_k02@mail.ru")
                        .roles(List.of(UserRole.PATIENT))
                        .enabled(true)
                        .build()
        );

        profileRepository.save(ProfileEntity.builder()
                .user(userRepository.findById(patient.getId()).get())
                .name("Виктор Кочнев")
                .profileType(ProfileEntity.ProfileType.PATIENT)
                .build());

        profileRepository.save(ProfileEntity.builder()
                .user(userRepository.findById(doctor.getId()).get())
                .name("Лапшин Никита")
                .profileType(ProfileEntity.ProfileType.DOCTOR)
                .build());

        profileRepository.save(ProfileEntity.builder()
                .user(userRepository.findById(admin.getId()).get())
                .name("Михайлов Елисей")
                .profileType(ProfileEntity.ProfileType.PATIENT)
                .build());

        workingTimeRepository.save(WorkingTimeEntity.builder()
                .user(doctor)
                .days(List.of(
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY
                ))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .zoneId(ZoneId.of("Europe/Moscow"))
                .build());

        var slotDuration = Duration.ofMinutes(30);
        consultationSlotRepository.save(ConsultationSlotEntity.builder()
                .duration(slotDuration)
                .specialization(terapevtSpecialization)
                .user(doctor)
                .build());
    }
}
