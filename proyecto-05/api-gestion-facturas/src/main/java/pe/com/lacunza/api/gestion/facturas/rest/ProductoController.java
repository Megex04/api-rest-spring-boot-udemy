package pe.com.lacunza.api.gestion.facturas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.pojo.Producto;
import pe.com.lacunza.api.gestion.facturas.service.ProductoService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;
import pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/products")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> agregarNuevoProducto(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return productoService.addNuevoProducto(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/list")
    public ResponseEntity<List<ProductoWrapper>> listarProductos() {
        try {
            return productoService.getAllProductos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping(path = "/update")
    public ResponseEntity<String> actualizarProducto(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return productoService.updateProducto(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") Integer id) {
        try {
            return productoService.deleteProducto(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductoWrapper>> buscarProductosPorNombre(@RequestParam("name") String name) {
        try {
            return productoService.searchProductByNameLike(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping(path = "/status")
    public ResponseEntity<String> modificarStatus(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return productoService.updateStatus(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/by/category/{id}")
    public ResponseEntity<List<ProductoWrapper>> obtenerProductosPorCategoria(@PathVariable("id") Integer id) {
        try {
            return productoService.getProductosByCategoria(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductoWrapper> obtenerProductosPorId(@PathVariable("id") Integer id) {
        try {
            return productoService.getProductoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductoWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
