package org.puravidagourmet.admin.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.TipoProducto;
import org.puravidagourmet.admin.domain.pojo.TipoProductoPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TipoProductoMapper {

  TipoProducto toTipoProducto(TipoProductoPojo tipoProductoPojo);

  List<TipoProductoPojo> toTipoProductoPojo(List<TipoProducto> tipoProductos);

  TipoProductoPojo toTipoProductoPojo(TipoProducto tipoProducto);
}
