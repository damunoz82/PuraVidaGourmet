package org.puravidatgourmet.api.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.entity.MateriaPrima;
import org.puravidatgourmet.api.domain.pojo.MateriaPrimaPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MateriaPrimaMapper {

  MateriaPrima toMateriaPrima(MateriaPrimaPojo materiaPrimaPojo);

  MateriaPrimaPojo toMateriaPrimaPojo(MateriaPrima materiaPrima);

  List<MateriaPrimaPojo> toMateriaPrimaPojo(List<MateriaPrima> materiaPrima);

}
