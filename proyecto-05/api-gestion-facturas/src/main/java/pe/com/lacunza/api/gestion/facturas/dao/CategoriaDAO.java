package pe.com.lacunza.api.gestion.facturas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.lacunza.api.gestion.facturas.pojo.Categoria;

import java.util.List;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {

    List<Categoria> getAllCatergorias();
}
