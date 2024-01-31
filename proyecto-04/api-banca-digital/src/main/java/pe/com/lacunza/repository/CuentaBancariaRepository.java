package pe.com.lacunza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.entities.CuentaBancaria;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {

}
