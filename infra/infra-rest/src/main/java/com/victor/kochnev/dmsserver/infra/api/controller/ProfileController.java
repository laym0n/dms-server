package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.profile.api.ProfileFacade;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileFacade profileFacade;

    @GetMapping("/profile")
    public ResponseEntity<ProfileModel> get() {
        var profile = profileFacade.getAuthenticatedProfile();
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileModel> create(@RequestBody ProfileModel profile) {
        profileFacade.signUp(profile);
        return ResponseEntity.ok(profile);
    }
}
