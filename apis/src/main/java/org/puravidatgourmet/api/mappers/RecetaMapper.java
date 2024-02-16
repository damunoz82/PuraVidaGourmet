package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.Receta;
import org.puravidatgourmet.api.domain.pojo.RecetaPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserMapper.class})
@Component
public interface RecetaMapper {

  Receta toReceta(RecetaPojo receta);

  RecetaPojo toRecetaPojo(Receta receta);

  List<RecetaPojo> toRecetas(List<Receta> recetas);
}
