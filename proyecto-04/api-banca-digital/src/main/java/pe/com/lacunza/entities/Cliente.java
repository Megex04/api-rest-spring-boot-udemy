package pe.com.lacunza.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    // un cliente tiene muchas cuentas bancarias
    @OneToMany(mappedBy = "cliente")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // al listar todos los clientes hay demasiada data(POR NO USAR DTOs) que produce un StackOverflowException en el servidor
    private List<CuentaBancaria> cuentasBancarias;
}
