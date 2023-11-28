package pe.com.lacunza.repository;

import org.springframework.stereotype.Repository;
import pe.com.lacunza.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductoRepository {

    private List<Producto> productos = new ArrayList<>();

    public ProductoRepository() {
        createProducts();
    }

    // genera/crea los productos desde "la BD" simulada
    public void createProducts() {
        productos.add(new Producto(1, "Producto X", 15, 300));
        productos.add(new Producto(2, "Producto Y", 43, 3400));
        productos.add(new Producto(3, "Producto Z", 78, 590));
        productos.add(new Producto(4, "Producto A", 61, 770));
        productos.add(new Producto(5, "Producto B", 13, 120));
        productos.add(new Producto(6, "Producto C", 41, 990));
        productos.add(new Producto(7, "Producto D", 38, 1200));
        productos.add(new Producto(8, "Producto E", 92, 3980));
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
    // buscamos un producto por su nombre
    public List<Producto> searchByName(String nombre) {
        return productos.stream()
                .filter(y -> y.getNombre().startsWith(nombre))
                .toList();
//                .collect(Collectors.toList());
    }
    // guardamos un producto
    public Producto save(Producto p) {
        Producto producto = new Producto();
        producto.setId(p.getId());
        producto.setNombre(p.getNombre());
        producto.setCantidad(p.getCantidad());
        producto.setPrecio(p.getPrecio());

        productos.add(producto);
        return producto;
    }
    // eliminamos un producto
    public String delete(Integer id) {
        productos.removeIf(y -> y.getId() == id);
        return null;
    }
    //actualizamos un producto
    public Producto update(Producto producto) {
        int idPos = -1;
        int id = 0;

        // comprobamos si existe el producto y obtenemos la posicion y el id del producto
        for(int i = 0; i < productos.size() ;i++) {
            if(productos.get(i).getId() == (producto.getId())) {
                id = producto.getId();
                idPos = i;
                break;
            }
        }

        if(idPos != -1) {
            Producto producto1 = new Producto();
            producto1.setId(id);
            producto1.setNombre(producto.getNombre());
            producto1.setCantidad(producto.getCantidad());
            producto1.setPrecio(producto.getPrecio());

            productos.set(idPos, producto1);
            return producto1;
        } else {
            return null;
        }
    }
}
