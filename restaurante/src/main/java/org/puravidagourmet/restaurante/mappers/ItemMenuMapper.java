package org.puravidagourmet.restaurante.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.ItemMenu;
import org.puravidagourmet.restaurante.domain.pojo.ItemMenuPojo;
import org.springframework.stereotype.Component;

@Mapper()
@Component
public interface ItemMenuMapper {

  ItemMenu toItemMenu(ItemMenuPojo itemMenuPojo);

  ItemMenuPojo toItemMenuPojo(ItemMenu itemMenu);

  List<ItemMenu> toItemMenu(List<ItemMenuPojo> itemMenuList);

  List<ItemMenuPojo> toItemMenuPojo(List<ItemMenu> itemMenuList);
}
