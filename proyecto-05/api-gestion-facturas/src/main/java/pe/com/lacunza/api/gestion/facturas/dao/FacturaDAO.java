package pe.com.lacunza.api.gestion.facturas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.api.gestion.facturas.pojo.Factura;

import java.util.List;

@Repository
public interface FacturaDAO extends JpaRepository<Factura, Integer> {

    List<Factura> getFacturas();

    List<Factura> getFacturaByUsername(@Param("username") String username);
}
