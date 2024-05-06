package pe.com.lacunza.api.gestion.facturas.service;

import org.springframework.http.ResponseEntity;
import pe.com.lacunza.api.gestion.facturas.pojo.Producto;
import pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper;

import java.util.List;
import java.util.Map;

public interface ProductoService {

    ResponseEntity<String> addNuevoProducto(Map<String, String> requestMap);

    ResponseEntity<List<ProductoWrapper>> getAllProductos();

    ResponseEntity<String> updateProducto(Map<String, String> requestMap);
    ResponseEntity<String> deleteProducto(Integer id);
    ResponseEntity<List<ProductoWrapper>> searchProductByNameLike(String name);
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    ResponseEntity<List<ProductoWrapper>> getProductosByCategoria(Integer id);
    ResponseEntity<ProductoWrapper> getProductoById(Integer id);
}
