package pe.com.lacunza.api_hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.api_hospital.model.Medico;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findByNombre(String nombre);

    List<Medico> findByEspecialidad(String especialidad);
}