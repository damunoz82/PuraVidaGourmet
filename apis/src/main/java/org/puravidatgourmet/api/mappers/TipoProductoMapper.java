package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.TipoProducto;
import org.puravidatgourmet.api.domain.pojo.TipoProductoPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TipoProductoMapper {

  TipoProducto toTipoProducto(TipoProductoPojo categoriaRecetaPojo);

  List<TipoProductoPojo> toTipoProductoPojo(List<TipoProducto> tipoProductos);

  TipoProductoPojo toTipoProductoPojo(TipoProducto tipoProducto);
}
