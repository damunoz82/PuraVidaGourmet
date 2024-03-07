package org.puravidagourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.InventarioDetalle;
import org.puravidagourmet.api.domain.pojo.InventarioDetallePojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InventarioDetalleMapper {

    List<InventarioDetallePojo> toInventartioDetallePojo(List<InventarioDetalle> inventarioDetalles);

    InventarioDetallePojo toInventartioDetallePojo(InventarioDetalle inventarioDetalles);
}
