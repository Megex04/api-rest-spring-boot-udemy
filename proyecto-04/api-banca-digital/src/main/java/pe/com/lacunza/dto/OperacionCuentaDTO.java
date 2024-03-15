package pe.com.lacunza.dto;

import lombok.Data;
import pe.com.lacunza.enums.TipoOperacion;

import java.util.Date;

@Data
public class OperacionCuentaDTO {

    private Long id;
    private Date fechaOperacion;
    private double monto;
    private TipoOperacion tipoOperacion;
    private String descripcion;
}
