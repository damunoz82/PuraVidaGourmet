package org.puravidatgourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.Inventario;
import org.puravidatgourmet.api.domain.pojo.InventarioPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = {UserMapper.class, DepartamentoMapper.class, InventarioDetalleMapper.class})
@Component
public interface InventarioMapper {

    InventarioPojo toInventarioPojo(Inventario inventario);


    List<InventarioPojo> toInventarioPojo(List<Inventario> inventario);
}
