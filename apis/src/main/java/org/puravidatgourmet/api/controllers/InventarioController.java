package org.puravidatgourmet.api.controllers;

import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.domain.entity.Inventario;
import org.puravidatgourmet.api.domain.pojo.InventarioPojo;
import org.puravidatgourmet.api.mappers.InventarioMapper;
import org.puravidatgourmet.api.services.InventarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

//    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole ('ADMIN')")
    public List<InventarioPojo> getAll() {
        try {
            LOGGER.info("START: getAll");
            return mapper.toInventarioPojo(inventarioService.getAll());
        }finally {
            LOGGER.info("END: getAll");
        }
    }
}
