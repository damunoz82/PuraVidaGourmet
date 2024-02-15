package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.Departamento;
import org.puravidatgourmet.api.domain.pojo.DepartamentoPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserMapper.class})
@Component
public interface DepartamentoMapper {

  Departamento toDepartamento(DepartamentoPojo categoriaRecetaPojo);

  List<DepartamentoPojo> toDepartamentoPojo(List<Departamento> tipoProductos);

  DepartamentoPojo toDepartamentoPojo(Departamento tipoProducto);
}
