package org.puravidatgourmet.api.db.repository;

import org.puravidatgourmet.api.domain.entity.Inventario;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface InventarioRepository {

//    @Procedure("iniciarInventario")
    int iniciarInventario(int departamentoId, int responsableId, String periodoMeta, String comentario, int estado, Integer idInventario);

    List<Inventario> findAll();

}
