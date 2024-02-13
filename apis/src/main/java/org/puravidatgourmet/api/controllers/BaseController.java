package org.puravidatgourmet.api.controllers;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BaseController {

  protected URI createLocation(String id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}
