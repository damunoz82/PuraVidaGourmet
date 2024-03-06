package org.puravidagourmet.api.services;

import org.puravidagourmet.api.db.repository.InventarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
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
