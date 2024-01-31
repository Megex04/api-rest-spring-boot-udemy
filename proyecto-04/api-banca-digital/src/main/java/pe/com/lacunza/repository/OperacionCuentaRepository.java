package pe.com.lacunza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.entities.OperacionCuenta;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long> {
}
