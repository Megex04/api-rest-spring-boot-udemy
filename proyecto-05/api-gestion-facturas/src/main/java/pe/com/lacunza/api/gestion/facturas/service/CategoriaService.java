package pe.com.lacunza.api.gestion.facturas.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoriaService {

    ResponseEntity<String> addNewCategoria(Map<String, String> requestMap);
}
