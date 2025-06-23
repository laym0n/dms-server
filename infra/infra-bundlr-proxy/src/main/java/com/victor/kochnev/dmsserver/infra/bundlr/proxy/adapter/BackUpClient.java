package com.victor.kochnev.dmsserver.infra.bundlr.proxy.adapter;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpRecord;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface BackUpClient {
    BackUpResult backUpRecord(UUID userId, BackUpRecord backUpRecord);

    BackUpResult backUpFile(MultipartFile file);
}
