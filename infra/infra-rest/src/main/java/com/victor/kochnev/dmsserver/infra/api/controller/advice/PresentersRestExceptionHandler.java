package com.victor.kochnev.dmsserver.infra.api.controller.advice;

import com.victor.kochnev.dmsserver.infra.api.controller.ControllerScanMarker;
import com.victor.kochnev.dmsserver.infra.common.advice.CommonRestExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackageClasses = ControllerScanMarker.class)
public class PresentersRestExceptionHandler extends CommonRestExceptionHandler {
}
