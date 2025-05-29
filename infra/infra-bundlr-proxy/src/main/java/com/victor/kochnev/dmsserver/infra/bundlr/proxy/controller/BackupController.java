package com.victor.kochnev.dmsserver.infra.bundlr.proxy.controller;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.adapter.BackUpClient;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpRecord;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BackupController {
    private final BackUpClient backUpClient;

    @PostMapping("/backup/record")
    public ResponseEntity<BackUpResult> create(@RequestBody BackUpRecord backUpRecord) {
        var backUpResult = backUpClient.backUpRecord(backUpRecord);
        return ResponseEntity.ok(backUpResult);
    }

    @PostMapping("/backup/file")
    public ResponseEntity<BackUpResult> uploadFile(@RequestPart("file") MultipartFile file) {
        var result = backUpClient.backUpFile(file);
        return ResponseEntity.ok(result);
    }
}
