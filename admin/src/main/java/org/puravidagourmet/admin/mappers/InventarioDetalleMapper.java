package org.puravidagourmet.admin.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.InventarioDetalle;
import org.puravidagourmet.admin.domain.pojo.InventarioDetallePojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface InventarioDetalleMapper {

  List<InventarioDetallePojo> toInventartioDetallePojo(List<InventarioDetalle> inventarioDetalles);

  InventarioDetallePojo toInventartioDetallePojo(InventarioDetalle inventarioDetalles);
}
