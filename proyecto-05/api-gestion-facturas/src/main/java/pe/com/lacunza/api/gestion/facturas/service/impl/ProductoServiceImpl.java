package pe.com.lacunza.api.gestion.facturas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.dao.ProductoDAO;
import pe.com.lacunza.api.gestion.facturas.pojo.Categoria;
import pe.com.lacunza.api.gestion.facturas.pojo.Producto;
import pe.com.lacunza.api.gestion.facturas.security.jwt.JwtFilter;
import pe.com.lacunza.api.gestion.facturas.service.ProductoService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;
import pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper;

import java.util.*;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNuevoProducto(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                // crear metodo para validar el producto
                if(validateProductoMap(requestMap, false)) {
                    productoDAO.save(getProductoFromMap(requestMap, false));
                    return FacturaUtils.getResponseEntity("Producto agregado con éxito", HttpStatus.CREATED);
                }
                return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getAllProductos() {
        try {
            return new ResponseEntity<>(productoDAO.getAllProductos(), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProducto(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                // crear metodo para validar el producto
                if(validateProductoMap(requestMap, true)) {
                    Optional<Producto> optionalProducto = productoDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if(optionalProducto.isPresent()) {
                        Producto producto = getProductoFromMap(requestMap, true);
                        producto.setStatus(requestMap.get("status"));
                        productoDAO.save(producto);
                        return FacturaUtils.getResponseEntity("Producto actualizado con éxito", HttpStatus.OK);
                    } else {
                        return FacturaUtils.getResponseEntity("Ese producto no existe", HttpStatus.NOT_FOUND);
                    }
                }
                return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProducto(Integer id) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<Producto> optionalProducto = productoDAO.findById(id);
                if(optionalProducto.isPresent()) {
                    productoDAO.deleteById(id);
                    return FacturaUtils.getResponseEntity("Producto eliminado con éxito", HttpStatus.OK);
                }else {
                    return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> searchProductByNameLike(String name) {
        try {
            List<ProductoWrapper> productoList= productoDAO.searchProductByNameLike(name);
            if(!productoList.isEmpty()) {
                return new ResponseEntity<>(productoList, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<Producto> optionalProducto = productoDAO.findById(Integer.parseInt(requestMap.get("id")));
                if(optionalProducto.isPresent()) {
                    productoDAO.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return FacturaUtils.getResponseEntity("STATUS del producto actualizado con éxito", HttpStatus.OK);
                }else {
                    return FacturaUtils.getResponseEntity("El producto no existe", HttpStatus.NOT_FOUND);
                }
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getProductosByCategoria(Integer id) {
        try {
            return new ResponseEntity<>(productoDAO.getProductosByCategoria(id), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductoWrapper> getProductoById(Integer id) {
        try {
            ProductoWrapper producto = productoDAO.getProductoById(id);
            if(Objects.nonNull(producto)) {
                return new ResponseEntity<>(productoDAO.getProductoById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ProductoWrapper(), HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductoWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Producto getProductoFromMap(Map<String, String> requestMap, boolean isAdd) {
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(requestMap.get("categoriaId")));

        Producto producto = new Producto();
        if(isAdd) {
            producto.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            producto.setStatus("true");
        }
        producto.setCategoria(categoria);
        producto.setNombre(requestMap.get("nombre"));
        producto.setDescripcion(requestMap.get("descripcion"));
        producto.setPrecio(Double.parseDouble(requestMap.get("precio")));
        return producto;
    }
    private boolean validateProductoMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("nombre")) {
            if(requestMap.containsKey("id") && validateId) {
                return true;
            }
            if(!validateId) {
                return true;
            }
        }
        return false;
    }
}
