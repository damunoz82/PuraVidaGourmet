package org.puravidagourmet.restaurante.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.DetalleOrden;
import org.puravidagourmet.restaurante.domain.pojo.DetalleOrdenPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DetalleOrdenMapper {

  List<DetalleOrdenPojo> toDetalleOrdenPojo(List<DetalleOrden> detalleOrdenes);

  List<DetalleOrden> toDetalleOrden(List<DetalleOrdenPojo> detalleOrdenes);

  DetalleOrdenPojo toDetalleOrdenPojo(DetalleOrden detalleOrdenes);

  DetalleOrden toDetalleOrden(DetalleOrdenPojo detalleOrdenes);
}
