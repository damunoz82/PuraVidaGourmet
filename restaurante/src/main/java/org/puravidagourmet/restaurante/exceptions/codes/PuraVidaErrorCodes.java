package org.puravidagourmet.restaurante.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum PuraVidaErrorCodes {
  AUTH_REC001(HttpStatus.UNAUTHORIZED, "auth.unAuthorized"),
  USU_REC004(HttpStatus.NOT_FOUND, "usuario.not_found"),
  TEMP(HttpStatus.NOT_FOUND, "");

  private HttpStatus statusCode;
  private String message;

  PuraVidaErrorCodes(HttpStatus statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return statusCode;
  }

  public String getErrorCode() {
    return message;
  }
}
