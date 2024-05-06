package pe.com.lacunza.api.gestion.facturas.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoWrapper {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String status;
    private Integer categoriaId;
    private String nombreCategoria;

    public ProductoWrapper(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public ProductoWrapper(Integer id, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

}
