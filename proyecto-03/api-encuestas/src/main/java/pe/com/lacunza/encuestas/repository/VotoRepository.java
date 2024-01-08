package pe.com.lacunza.encuestas.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.encuestas.model.Opcion;
import pe.com.lacunza.encuestas.model.Voto;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

    @Query(value = "SELECT v.* FROM Opcion o, Voto v WHERE o.ENCUESTA_ID = ?1 AND v.OPCION_ID=o.OPCION_ID", nativeQuery = true)
    Iterable<Voto> findByEncuesta(Long encuestaId);
}