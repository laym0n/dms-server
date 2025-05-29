package com.victor.kochnev.dmsserver.infra.bundlr.proxy.adapter;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpRecord;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpResult;
import org.springframework.web.multipart.MultipartFile;

public interface BackUpClient {
    BackUpResult backUpRecord(BackUpRecord backUpRecord);

    BackUpResult backUpFile(MultipartFile file);
}
