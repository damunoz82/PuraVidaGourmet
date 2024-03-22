package org.puravidagourmet.restaurante.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.Orden;
import org.puravidagourmet.restaurante.domain.pojo.OrdenPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = {DetalleOrdenMapper.class, UsuarioMapper.class})
@Component
public interface OrdenMapper {

  Orden toOrden(OrdenPojo orden);

  OrdenPojo toOrdenPojo(Orden orden);

  List<OrdenPojo> toOrdenPojo(List<Orden> ordenes);
}
