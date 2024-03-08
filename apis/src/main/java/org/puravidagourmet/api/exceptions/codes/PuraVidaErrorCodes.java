package org.puravidagourmet.api.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum PuraVidaErrorCodes {
  // CATEGORIA DE RECETA
  CAT_REC001(HttpStatus.BAD_REQUEST, "categoria.receta.duplicado"),
  CAT_REC002(HttpStatus.NOT_FOUND, "categoria.receta.not_found"),

  // DEPARTAMENTO
  DEP_REC001(HttpStatus.BAD_REQUEST, "departamento.duplicado"),
  DEP_REC002(HttpStatus.NOT_FOUND, "departamento.not_found"),

  // TIPO PRODUCTO
  TPROD_REC001(HttpStatus.BAD_REQUEST, "tipo.producto.duplicado"),
  TPROD_REC002(HttpStatus.NOT_FOUND, "tipo.producto.not_found"),

  // PRODUCTO
  PROD_REC001(HttpStatus.BAD_REQUEST, "producto.duplicado"),
  PROD_REC002(HttpStatus.NOT_FOUND, "producto.not_found"),

  // RECETA
  RECETA_REC001(HttpStatus.BAD_REQUEST, "receta.duplicado"),
  RECETA_REC002(HttpStatus.BAD_REQUEST, "receta.ingrediente.inexistente"),
  RECETA_REC003(HttpStatus.NOT_FOUND, "receta.not_found"),

  // Inventario
  INVENT_REC001(HttpStatus.NOT_FOUND, "inventario.not_found"),

  // AUTH
  AUTH_REC001(HttpStatus.UNAUTHORIZED, "auth.unAuthorized"),

  // USUARIOS
  USU_REC001(HttpStatus.BAD_REQUEST, "usuario.email.duplicado"),
  USU_REC002(HttpStatus.BAD_REQUEST, "usuario.disable.self"),
  USU_REC003(HttpStatus.BAD_REQUEST, "usuario.action.no.encontrado"),
  USU_REC004(HttpStatus.NOT_FOUND, "usuario.not_found");

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
