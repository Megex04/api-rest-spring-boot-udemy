package pe.com.lacunza.dto;

import lombok.Data;
import pe.com.lacunza.enums.EstadoCuenta;

import java.util.Date;

@Data
public class CuentaAhorroDTO extends CuentaBancariaDTO {

    private String id;
    private double balance;
    private Date fechaCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double tasaDeInteres;

}
