package pe.com.lacunza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
