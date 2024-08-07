package pe.com.lacunza.api_hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.Consulta;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByCita(Cita cita);

    List<Consulta> findByInformeContainingIgnoreCase(String searchTerm);
}