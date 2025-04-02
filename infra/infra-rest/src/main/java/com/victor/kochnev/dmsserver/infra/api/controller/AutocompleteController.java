package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.profile.api.SpecializationFacade;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AutocompleteController {
    private final SpecializationFacade specializationFacade;

    @GetMapping("/autocomplete/specialization")
    public ResponseEntity<ModelsResponseDto<SpecializationModel>> getCurrentUserConsultations() {
        var specializationsResponse = specializationFacade.findAll();
        return ResponseEntity.ok(specializationsResponse);
    }
}
