package com.ocdl.client.exception;

public class CFDException extends RuntimeException {

  private String frontEndErrorMsg;

  public CFDException(String frontEndErrorMsg) {
    super(frontEndErrorMsg);
    this.frontEndErrorMsg = frontEndErrorMsg;
  }

  public String getFrontEndErrorMsg() {
    return frontEndErrorMsg;
  }
}
