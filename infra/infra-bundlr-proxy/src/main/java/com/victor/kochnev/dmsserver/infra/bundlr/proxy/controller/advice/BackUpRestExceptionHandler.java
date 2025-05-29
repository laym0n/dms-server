package com.victor.kochnev.dmsserver.infra.bundlr.proxy.controller.advice;

import com.victor.kochnev.dmsserver.infra.bundlr.proxy.controller.BackupController;
import com.victor.kochnev.dmsserver.infra.common.advice.CommonRestExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BackupController.class)
public class BackUpRestExceptionHandler extends CommonRestExceptionHandler {
}
