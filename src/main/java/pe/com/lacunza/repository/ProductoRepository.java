package pe.com.lacunza.repository;

import org.springframework.stereotype.Repository;
import pe.com.lacunza.model.Producto;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoRepository {

    private List<Producto> productos = new ArrayList<>();

    // genera/crea los productos desde "la BD" simulada
    public void createProducts() {
        productos = List.of(
                new Producto(1, "Producto X", 15, 300),
                new Producto(2, "Producto Y", 43, 3400),
                new Producto(3, "Producto Z", 78, 590),
                new Producto(4, "Producto A", 61, 770),
                new Producto(5, "Producto B", 13, 120),
                new Producto(6, "Producto C", 41, 990),
                new Producto(7, "Producto D", 38, 1200),
                new Producto(8, "Producto E", 92, 3980)
        );
    }

    // listar productos
    public List<Producto> getAllProducts() {
        return productos;
    }

    // buscar producto
    public Producto findById(int id) {
        for(int i = 0;i < productos.size(); i++) {
            if(productos.get(i).getId() == id) {
                return productos.get(i);
            }
        }
        return null;
    }


}
