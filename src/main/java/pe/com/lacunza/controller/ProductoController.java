package pe.com.lacunza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.model.Producto;
import pe.com.lacunza.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.getProductos();
    }
    @GetMapping("/{id}")
    public Producto buscarProducto(@PathVariable int id) {
        return productoService.getProductoById(id);
    }
    @PutMapping
    public Producto actualizarProducto(@RequestBody Producto producto) {
        return productoService.updateProducto(producto);
    }
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable int id) {
        return productoService.deleteProducto(id);
    }
}
