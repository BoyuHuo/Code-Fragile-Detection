package com.ocdl.client.util;

import com.ocdl.client.exception.CFDException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = CFDException.class)
  public Response exceptionsHandling(CFDException exception) {
    String frontErrorMsg = exception.getFrontEndErrorMsg();

    return Response.getBuilder().setCode(Response.Code.ERROR).setMessage(frontErrorMsg).build();
  }
}
