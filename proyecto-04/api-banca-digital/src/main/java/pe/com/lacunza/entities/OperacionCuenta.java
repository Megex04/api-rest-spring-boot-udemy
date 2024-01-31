package pe.com.lacunza.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.lacunza.enums.TipoOperacion;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacionCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaOperacion;

    private double monto;

    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;

    @ManyToOne
    private CuentaBancaria cuentaBancaria;

    private String descripcion;
}
