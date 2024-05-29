package pe.com.lacunza.api.gestion.facturas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.lacunza.api.gestion.facturas.service.DashboardService;

import java.util.Map;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getCounts() {
        return dashboardService.getCount();
    }
}
