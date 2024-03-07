package org.puravidagourmet.api.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.puravidagourmet.api.domain.enums.EstadoInventario;

import java.sql.Date;
import java.util.List;

@Data
@ToString
public class InventarioPojo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date fecha_creacion;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date fecha_modificacion;

    private String comentario;

    private DepartamentoPojo departamento;

    private UserPojo responsable;

    private String periodoMeta;

    private EstadoInventario estado;

    private List<InventarioDetallePojo> detalle;
}
