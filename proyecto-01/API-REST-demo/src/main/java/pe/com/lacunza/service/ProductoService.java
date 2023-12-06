package pe.com.lacunza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.lacunza.model.Producto;
import pe.com.lacunza.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    public List<Producto> getProductos() {
        return productoRepository.getAllProducts();
    }
    public Producto getProductoById(int id) {
        return productoRepository.findById(id);
    }
    public String deleteProducto(int id) {
        Producto producto = productoRepository.findById(id);
        if(producto != null) {
            productoRepository.delete(id);
            return "Producto eliminado con id: " + id;
        } else {
            return "no existe el producto con id: " + id;
        }
    }
    public Producto updateProducto(Producto producto) {
        return productoRepository.update(producto);
    }
}
