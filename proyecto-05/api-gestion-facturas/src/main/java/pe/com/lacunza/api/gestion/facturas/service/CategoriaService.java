package pe.com.lacunza.api.gestion.facturas.service;

import org.springframework.http.ResponseEntity;
import pe.com.lacunza.api.gestion.facturas.pojo.Categoria;

import java.util.List;
import java.util.Map;

public interface CategoriaService {

    ResponseEntity<String> addNewCategoria(Map<String, String> requestMap);
    ResponseEntity<List<Categoria>> getAllCategorias(String valueFilter);
    ResponseEntity<String> updateCategoria(Map<String, String> requestMap);
}
