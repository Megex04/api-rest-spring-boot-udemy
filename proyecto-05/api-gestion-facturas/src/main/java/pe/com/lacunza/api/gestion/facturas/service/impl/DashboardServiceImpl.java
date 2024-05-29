package pe.com.lacunza.api.gestion.facturas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api.gestion.facturas.dao.CategoriaDAO;
import pe.com.lacunza.api.gestion.facturas.dao.FacturaDAO;
import pe.com.lacunza.api.gestion.facturas.dao.ProductoDAO;
import pe.com.lacunza.api.gestion.facturas.service.DashboardService;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private FacturaDAO facturaDAO;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("productos", this.productoDAO.count());
        map.put("categorias", this.categoriaDAO.count());
        map.put("facturas", this.facturaDAO.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
