package org.puravidatgourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.InventarioDetalle;
import org.puravidatgourmet.api.domain.pojo.InventarioDetallePojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InventarioDetalleMapper {

    List<InventarioDetallePojo> toInventartioDetallePojo(List<InventarioDetalle> inventarioDetalles);

    InventarioDetallePojo toInventartioDetallePojo(InventarioDetalle inventarioDetalles);
}
