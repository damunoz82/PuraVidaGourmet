package org.puravidagourmet.admin.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.SeccionMenu;
import org.puravidagourmet.admin.domain.pojo.SeccionesMenuPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {ItemMenuMapper.class})
@Component
public interface SeccionMenuMapper {

  SeccionMenu toSeccionMenu(SeccionesMenuPojo seccionesMenuPojo);

  SeccionesMenuPojo toSeccionMenuPojo(SeccionMenu seccionMenu);
}
