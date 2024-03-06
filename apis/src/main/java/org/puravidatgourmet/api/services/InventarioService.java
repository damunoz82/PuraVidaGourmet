package org.puravidatgourmet.api.services;

import org.puravidatgourmet.api.db.repository.InventarioRepository;
import org.puravidatgourmet.api.domain.entity.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

//    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> getAll() {
        return inventarioRepository.findAll();
    }
}
