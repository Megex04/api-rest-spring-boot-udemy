package pe.com.lacunza.api.gestion.facturas.service;

import org.springframework.http.ResponseEntity;
import pe.com.lacunza.api.gestion.facturas.pojo.Factura;

import java.util.List;
import java.util.Map;

public interface FacturaService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Factura>> getFacturas();

    ResponseEntity<byte[]> getPdfReport(Map<String, Object> requestMap);

    ResponseEntity<String> deleteFactura(Integer id);
}
