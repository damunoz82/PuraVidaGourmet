package org.puravidagourmet.restaurante.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.SeccionMenu;
import org.puravidagourmet.restaurante.domain.pojo.SeccionesMenuPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {ItemMenuMapper.class})
@Component
public interface SeccionMenuMapper {

  SeccionMenu toSeccionMenu(SeccionesMenuPojo seccionesMenuPojo);

  SeccionesMenuPojo toSeccionMenuPojo(SeccionMenu seccionMenu);
}
