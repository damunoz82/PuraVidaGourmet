package org.puravidatgourmet.api.domain.converters;

import org.puravidatgourmet.api.domain.enums.UnidadMedidas;

public class UnidadMedidaConverter  {

    public Integer convertToDatabaseColumn(UnidadMedidas unidadMedidas) {
        return unidadMedidas.ordinal();
    }

    public UnidadMedidas convertToEntityAttribute(Integer codigo) {
        return UnidadMedidas.getUnidadMedida(codigo);
    }
}
