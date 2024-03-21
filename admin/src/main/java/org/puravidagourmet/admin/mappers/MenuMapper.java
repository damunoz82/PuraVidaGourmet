package org.puravidagourmet.admin.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.Menu;
import org.puravidagourmet.admin.domain.pojo.MenuPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {SeccionMenuMapper.class, UsuarioMapper.class})
@Component
public interface MenuMapper {

  Menu toMenu(MenuPojo menuPojo);

  MenuPojo toMenuPojo(Menu menu);

  List<Menu> toMenu(List<MenuPojo> menuPojoList);

  List<MenuPojo> toMenuPojo(List<Menu> menuList);
}
