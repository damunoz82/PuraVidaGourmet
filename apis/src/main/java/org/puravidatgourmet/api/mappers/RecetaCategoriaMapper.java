package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.RecetaCategoria;
import org.puravidatgourmet.api.domain.pojo.RecetaCategoriaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecetaCategoriaMapper {

  RecetaCategoria toCategoriaReceta(RecetaCategoriaPojo categoriaRecetaPojo);

  List<RecetaCategoriaPojo> toRecetaCategoriaPojo(List<RecetaCategoria> recetaCategorias);

  RecetaCategoriaPojo toRecetaCategoriaPojo(RecetaCategoria recetaCategoria);
}
