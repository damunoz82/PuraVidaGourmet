package org.puravidagourmet.restaurante.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.Menu;
import org.puravidagourmet.restaurante.domain.pojo.MenuPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {SeccionMenuMapper.class, UsuarioMapper.class})
@Component
public interface MenuMapper {

  Menu toMenu(MenuPojo menuPojo);

  MenuPojo toMenuPojo(Menu menu);

  List<Menu> toMenu(List<MenuPojo> menuPojoList);

  List<MenuPojo> toMenuPojo(List<Menu> menuList);
}
