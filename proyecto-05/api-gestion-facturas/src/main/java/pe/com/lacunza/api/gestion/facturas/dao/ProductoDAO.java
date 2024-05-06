package pe.com.lacunza.api.gestion.facturas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lacunza.api.gestion.facturas.pojo.Producto;
import pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper;

import java.util.List;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Integer> {

    List<ProductoWrapper> getAllProductos();
    List<ProductoWrapper> searchProductByNameLike(@Param("nombre") String nombre);

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProductoWrapper> getProductosByCategoria(Integer id);

    ProductoWrapper getProductoById(Integer id);
}
