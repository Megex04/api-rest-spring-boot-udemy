package pe.com.lacunza.pruebas_unitarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.pruebas_unitarias.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


}