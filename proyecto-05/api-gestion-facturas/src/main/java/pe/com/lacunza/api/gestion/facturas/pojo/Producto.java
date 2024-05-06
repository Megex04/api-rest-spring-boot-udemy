package pe.com.lacunza.api.gestion.facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "Producto.getAllProductos", query = "SELECT new pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper" +
        "(p.id,p.nombre,p.descripcion,p.precio,p.status,p.categoria.id,p.categoria.nombre) FROM Producto p")

@NamedQuery(name = "Producto.searchProductByNameLike", query = "SELECT new pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper" +
        "(p.id,p.nombre,p.descripcion,p.precio,p.status,p.categoria.id,p.categoria.nombre) FROM Producto p WHERE p.nombre LIKE CONCAT('%',:nombre,'%')")

@NamedQuery(name = "Producto.updateStatus", query = "UPDATE Producto p SET p.status=:status WHERE p.id=:id")
@NamedQuery(name = "Producto.getProductosByCategoria", query = "SELECT new pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper" +
        "(p.id,p.nombre) FROM Producto p WHERE p.categoria.id = ?1 AND p.status='true'")
@NamedQuery(name = "Producto.getProductoById", query = "SELECT new pe.com.lacunza.api.gestion.facturas.wrapper.ProductoWrapper" +
        "(p.id,p.nombre,p.descripcion,p.precio) FROM Producto p WHERE p.id = ?1")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_fk", nullable = false,referencedColumnName = "id")
    private Categoria categoria;
}
