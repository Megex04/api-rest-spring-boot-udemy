package pe.com.lacunza.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.lacunza.enums.EstadoCuenta;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "TIPO", length = 4, discriminatorType = DiscriminatorType.STRING) // si es necesario eliminar: discriminatorType = DiscriminatorType.STRING
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CuentaBancaria {

    @Id
    private String id;

    private double balance;

    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    private List<OperacionCuenta> operacionesCuenta;
}
