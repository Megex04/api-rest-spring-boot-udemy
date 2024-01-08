package pe.com.lacunza.encuestas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) salia error al listar VOTOS con esto se soluciona (al instructor)
public class Opcion {

    @Id
    @GeneratedValue
    @Column(name = "OPCION_ID")
    private Long id;

    private String value;
}
