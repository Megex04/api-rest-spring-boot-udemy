package pe.com.lacunza.dto;

import lombok.Data;

@Data
public class DebitoDTO {

    private String cuentaId;
    private double monto;
    private String descripcion;
}
