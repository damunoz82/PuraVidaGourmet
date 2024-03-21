package org.puravidagourmet.admin.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.ItemMenu;
import org.puravidagourmet.admin.domain.pojo.ItemMenuPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper()
@Component
public interface ItemMenuMapper {

  ItemMenu toItemMenu(ItemMenuPojo itemMenuPojo);

  ItemMenuPojo toItemMenuPojo(ItemMenu itemMenu);

  List<ItemMenu> toItemMenu(List<ItemMenuPojo> itemMenuList);

  List<ItemMenuPojo> toItemMenuPojo(List<ItemMenu> itemMenuList);
}
