package pe.com.lacunza.dto;

import lombok.Data;

@Data
public class TransferenciaRequestDTO {

    private String cuentaPropietario;
    private String cuentaDestinatario;
    private double monto;
    private String descripcion;
}
