package org.puravidagourmet.admin.exceptions;

import org.puravidagourmet.admin.exceptions.codes.PuraVidaErrorCodes;
import org.springframework.web.server.ResponseStatusException;

public class PuraVidaExceptionHandler extends ResponseStatusException {

  public PuraVidaExceptionHandler(PuraVidaErrorCodes code) {
    super(code.getStatus(), code.getErrorCode());
  }

  public PuraVidaExceptionHandler(PuraVidaErrorCodes code, Object... args) {
    super(code.getStatus(), code.getErrorCode(), null, code.name(), args);
  }

  public PuraVidaExceptionHandler(PuraVidaErrorCodes code, Throwable throwable) {
    super(code.getStatus(), code.getErrorCode(), throwable);
  }
}
