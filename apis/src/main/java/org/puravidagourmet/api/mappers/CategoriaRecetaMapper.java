package org.puravidagourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.domain.pojo.CategoriaRecetaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CategoriaRecetaMapper {

  CategoriaReceta toCategoriaReceta(CategoriaRecetaPojo categoriaRecetaPojo);

  List<CategoriaRecetaPojo> toCategoriaRecetaPojo(List<CategoriaReceta> tipoProductos);

  CategoriaRecetaPojo toCategoriaRecetaPojo(CategoriaReceta tipoProducto);
}
