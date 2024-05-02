package pe.com.lacunza.api.gestion.facturas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.pojo.Categoria;
import pe.com.lacunza.api.gestion.facturas.service.CategoriaService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/categories")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @PostMapping(path = "/add")
    public ResponseEntity<String> agregarCategoria(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return categoriaService.addNewCategoria(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/list")
    public ResponseEntity<List<Categoria>> listarCategorias(@RequestParam(name = "valueFilter", required = false) String valueFilter) {
        try {
            return categoriaService.getAllCategorias(valueFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> actualizarCategoria(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return categoriaService.updateCategoria(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
