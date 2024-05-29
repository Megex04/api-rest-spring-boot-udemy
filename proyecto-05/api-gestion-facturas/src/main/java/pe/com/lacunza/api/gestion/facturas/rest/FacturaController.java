package pe.com.lacunza.api.gestion.facturas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.pojo.Factura;
import pe.com.lacunza.api.gestion.facturas.service.FacturaService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping(value = "/generar/reporte")
    public ResponseEntity<String> generarReporte(@RequestBody Map<String, Object> requestMap) {
        try {
            return facturaService.generateReport(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/get/facturas")
    public ResponseEntity<List<Factura>> listarFacturas() {
        try {
            return facturaService.getFacturas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/get/pdf")
    public ResponseEntity<byte[]> obtenerPdf(@RequestBody Map<String, Object> requestMap) {
        try {
            return facturaService.getPdfReport(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteFactura(@PathVariable("id") Integer id) {
        try {
            return facturaService.deleteFactura(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
