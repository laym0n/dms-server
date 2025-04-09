package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.profile.api.ProfileFacade;
import com.victor.kochnev.dmsserver.profile.dto.ProfileFiltersDto;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileFacade profileFacade;

    @GetMapping("/profile")
    @Operation(operationId = "getProfileInfo")
    public ResponseEntity<ProfileModel> get(@RequestParam(name = "userId", required = false) UUID userId) {
        ProfileModel profile;
        if (userId != null) {
            profile = profileFacade.getInfoByUserId(userId);
        } else {
            profile = profileFacade.getAuthenticatedProfile();
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileModel> create(@RequestBody ProfileModel profile) {
        profileFacade.signUp(profile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile/search")
    public ResponseEntity<ModelsResponseDto<? extends ProfileModel>> search(@RequestBody ModelsRequestDto<ProfileFiltersDto> requestDto) {
        var modelsResponse = profileFacade.search(requestDto);
        return ResponseEntity.ok(modelsResponse);
    }
}
