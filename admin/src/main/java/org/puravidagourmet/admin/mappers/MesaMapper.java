package org.puravidagourmet.admin.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.Mesa;
import org.puravidagourmet.admin.domain.pojo.MesaPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MesaMapper {

  Mesa toMesa(MesaPojo mesaPojo);

  MesaPojo toMesaPojo(Mesa mesa);

  List<MesaPojo> toMesaPojoList(List<Mesa> list);
}
