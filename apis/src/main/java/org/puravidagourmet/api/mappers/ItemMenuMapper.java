package org.puravidagourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.ItemMenu;
import org.puravidagourmet.api.domain.pojo.ItemMenuPojo;
import org.springframework.stereotype.Component;

import java.awt.event.ItemEvent;
import java.util.List;

@Mapper()
@Component
public interface ItemMenuMapper {

  ItemMenu toItemMenu(ItemMenuPojo itemMenuPojo);

  ItemMenuPojo toItemMenuPojo(ItemMenu itemMenu);

  List<ItemMenu> toItemMenu(List<ItemMenuPojo> itemMenuList);

  List<ItemMenuPojo> toItemMenuPojo(List<ItemMenu> itemMenuList);
}
