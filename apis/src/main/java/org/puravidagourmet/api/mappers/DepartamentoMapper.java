package org.puravidagourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.domain.pojo.DepartamentoPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserMapper.class})
@Component
public interface DepartamentoMapper {

  Departamento toDepartamento(DepartamentoPojo categoriaRecetaPojo);

  List<DepartamentoPojo> toDepartamentoPojo(List<Departamento> tipoProductos);

  DepartamentoPojo toDepartamentoPojo(Departamento tipoProducto);
}
