package pe.com.lacunza.api.gestion.facturas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.dao.CategoriaDAO;
import pe.com.lacunza.api.gestion.facturas.pojo.Categoria;
import pe.com.lacunza.api.gestion.facturas.pojo.User;
import pe.com.lacunza.api.gestion.facturas.security.jwt.JwtFilter;
import pe.com.lacunza.api.gestion.facturas.service.CategoriaService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategoria(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                // crear metodo para validar la categoria
                if(validateCategoriaMap(requestMap, false)) {
                    categoriaDAO.save(getCategoriaFromMap(requestMap, false));
                    return FacturaUtils.getResponseEntity("Categoria agregada con éxito", HttpStatus.CREATED);
                }
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoriaMap(Map<String, String> requestMap, boolean validateId) {
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
    private Categoria getCategoriaFromMap(Map<String, String> requestMap, Boolean isAdd) {
        Categoria categoria = new Categoria();
        if(isAdd) {
            categoria.setId(Integer.parseInt(requestMap.get("id")));
        }
        categoria.setNombre(requestMap.get("nombre"));
        return categoria;
    }
}
