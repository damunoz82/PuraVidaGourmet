package org.puravidagourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.domain.pojo.RecetaPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserMapper.class, CategoriaRecetaMapper.class})
@Component
public interface RecetaMapper {

  @Mapping(target = "categoriaReceta", source = "categoria")
  Receta toReceta(RecetaPojo receta);

  @Mapping(source = "categoriaReceta", target = "categoria")
  RecetaPojo toRecetaPojo(Receta receta);

  List<RecetaPojo> toRecetas(List<Receta> recetas);
}
