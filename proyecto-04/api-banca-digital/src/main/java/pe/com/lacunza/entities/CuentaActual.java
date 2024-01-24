package pe.com.lacunza.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CA") // current account
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaActual extends CuentaBancaria {

    private double sobregiro;
}
