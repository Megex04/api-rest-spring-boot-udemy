package pe.com.lacunza.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SA") // saving account
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaAhorro extends CuentaBancaria {

    private double tasaDeInteres;
}
