package org.puravidagourmet.restaurante.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum PuraVidaErrorCodes {
  // pre-required
  AUTH_REC001(HttpStatus.UNAUTHORIZED, "auth.unAuthorized"),
  USU_REC003(HttpStatus.BAD_REQUEST, "usuario.action.no.encontrado"),
  USU_REC004(HttpStatus.NOT_FOUND, "usuario.not_found"),

  // orden
  ORD_001(HttpStatus.NOT_FOUND, "orden.not_found"),
  ORD_002(HttpStatus.BAD_REQUEST, "orden.item_not_found");

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
