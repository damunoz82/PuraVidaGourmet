package org.puravidatgourmet.security.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.security.domain.entity.Receta;
import org.puravidatgourmet.security.domain.pojo.RecetaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecetaMapper {

  Receta toReceta(RecetaPojo receta);

  RecetaPojo toRecetaPojo(Receta receta);

  List<RecetaPojo> toRecetas(List<Receta> recetas);
}
