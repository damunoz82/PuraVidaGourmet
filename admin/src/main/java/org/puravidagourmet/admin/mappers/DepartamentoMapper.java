package org.puravidagourmet.admin.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.Departamento;
import org.puravidagourmet.admin.domain.pojo.DepartamentoPojo;
import org.springframework.stereotype.Component;

@Mapper(uses = {UsuarioMapper.class})
@Component
public interface DepartamentoMapper {

  Departamento toDepartamento(DepartamentoPojo categoriaRecetaPojo);

  List<DepartamentoPojo> toDepartamentoPojo(List<Departamento> tipoProductos);

  DepartamentoPojo toDepartamentoPojo(Departamento tipoProducto);
}
