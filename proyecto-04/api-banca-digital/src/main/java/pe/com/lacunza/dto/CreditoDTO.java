package pe.com.lacunza.dto;

import lombok.Data;

@Data
public class CreditoDTO {

    private String cuentaId;
    private double monto;
    private String descripcion;
}
