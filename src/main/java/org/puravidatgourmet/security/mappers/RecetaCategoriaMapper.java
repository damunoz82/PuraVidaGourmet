package org.puravidatgourmet.security.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.security.domain.entity.RecetaCategoria;
import org.puravidatgourmet.security.domain.pojo.RecetaCategoriaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecetaCategoriaMapper {

  RecetaCategoria toCategoriaReceta(RecetaCategoriaPojo categoriaRecetaPojo);

  List<RecetaCategoriaPojo> toRecetaCategoriaPojo(List<RecetaCategoria> recetaCategorias);

  RecetaCategoriaPojo toRecetaCategoriaPojo(RecetaCategoria recetaCategoria);
}
