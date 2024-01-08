package pe.com.lacunza.encuestas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.encuestas.model.Opcion;

@Repository
public interface OpcionRepository extends CrudRepository<Opcion, Long> {
    
}