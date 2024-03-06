package org.puravidatgourmet.api.domain.converters;

import org.puravidatgourmet.api.domain.enums.EstadoInventario;

public class EstadoInventarioConverter  {

    public Integer convertToDatabaseColumn(EstadoInventario estadoInventario) {
        return estadoInventario.ordinal();
    }

    public EstadoInventario convertToEntityAttribute(Integer codigo) {
        return EstadoInventario.getFormatoCompra(codigo);
    }
}
