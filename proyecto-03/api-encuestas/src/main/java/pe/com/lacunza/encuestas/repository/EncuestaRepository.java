package pe.com.lacunza.encuestas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.encuestas.model.Encuesta;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {

}