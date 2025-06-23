package com.victor.kochnev.dmsserver.infra.bundlr.proxy.adapter;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpRecord;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.BackUpResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BundlrArweaveBackUpAdapter implements BackUpClient {
    @Qualifier("bundlrProxyRestClient")
    private final RestClient restClient;

    @Override
    public BackUpResult backUpRecord(UUID userId, BackUpRecord backUpRecord) {
        return restClient.post()
                .uri("/upload/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(backUpRecord)
                .retrieve()
                .toEntity(BackUpResult.class)
                .getBody();
    }

    @Override
    public BackUpResult backUpFile(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        return restClient.post()
                .uri("/upload-web3")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(builder.build())
                .retrieve()
                .toEntity(BackUpResult.class)
                .getBody();
    }
}
