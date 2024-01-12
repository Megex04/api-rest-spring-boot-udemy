package pe.com.lacunza.encuestas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encuesta {

    @Id
    @GeneratedValue
    @Column(name = "ENCUESTA_ID")
    private Long id;

    @Column(name = "pregunta")
    @NotEmpty
    private String pregunta;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ENCUESTA_ID")
    @OrderBy
    @Size(min=2, max=6)
    private Set<Opcion> opciones;
}
