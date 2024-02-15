package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.Producto;
import org.puravidatgourmet.api.domain.pojo.ProductoPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {TipoProductoMapper.class})
@Component
public interface ProductoMapper {

  Producto toProducto(ProductoPojo productoPojo);

  ProductoPojo toProductoPojo(Producto producto);

  List<ProductoPojo> toProductoPojo(List<Producto> producto);
}
