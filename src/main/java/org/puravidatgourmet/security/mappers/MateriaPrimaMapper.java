package org.puravidatgourmet.security.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.security.domain.entity.MateriaPrima;
import org.puravidatgourmet.security.domain.pojo.MateriaPrimaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MateriaPrimaMapper {

  MateriaPrima toMateriaPrima(MateriaPrimaPojo materiaPrimaPojo);

  MateriaPrimaPojo toMateriaPrimaPojo(MateriaPrima materiaPrima);

  List<MateriaPrimaPojo> toMateriaPrimaPojo(List<MateriaPrima> materiaPrima);

}
