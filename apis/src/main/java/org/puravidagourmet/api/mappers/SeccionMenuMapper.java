package org.puravidagourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.SeccionMenu;
import org.puravidagourmet.api.domain.pojo.SeccionesMenuPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {ItemMenuMapper.class})
@Component
public interface SeccionMenuMapper {

  SeccionMenu toSeccionMenu(SeccionesMenuPojo seccionesMenuPojo);

  SeccionesMenuPojo toSeccionMenuPojo(SeccionMenu seccionMenu);
}
