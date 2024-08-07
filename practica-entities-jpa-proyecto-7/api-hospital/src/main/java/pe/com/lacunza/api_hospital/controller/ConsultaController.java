package pe.com.lacunza.api_hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api_hospital.dto.ConsultaDTO;
import pe.com.lacunza.api_hospital.mapper.CitaMapper;
import pe.com.lacunza.api_hospital.service.CitaService;
import pe.com.lacunza.api_hospital.service.ConsultaService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private CitaService citaService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<ConsultaDTO>> listarConsultas() {
        List<ConsultaDTO> consultas = consultaService.getAllConsultas();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsultaDTO> obtenerConsulta(@PathVariable(value = "id") Long id) {
        Optional<ConsultaDTO> consulta = consultaService.getConsultaById(id);
        return consulta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<ConsultaDTO> crearConsulta(@RequestParam(name = "citaId") Long citaId, @RequestBody ConsultaDTO consultaDTO) throws Exception {
        ConsultaDTO createdCitaDTO = consultaService.createConsulta(citaId, consultaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCitaDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ConsultaDTO> actualizarConsulta(@PathVariable(value = "id") Long id, @RequestBody ConsultaDTO consultaDTO) throws Exception {
        ConsultaDTO updatedCitaDTO = consultaService.updateConsulta(id, consultaDTO);
        return updatedCitaDTO != null ? ResponseEntity.ok(updatedCitaDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> borrarConsulta(@PathVariable(value = "id") Long id) {
        consultaService.deleteConsulta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasPorInforme(@RequestParam(value = "informe") String informe) {
        List<ConsultaDTO> consultaByInforme = consultaService.getConsultasByInformeContaining(informe);
        return ResponseEntity.ok(consultaByInforme);
    }

    @GetMapping(value = "/cita/{citaId}")
    public ResponseEntity<List<ConsultaDTO>> obtenerConsultasPorCita(@PathVariable(value = "citaId") Long id) throws ParseException {
        List<ConsultaDTO> consultaByCita = consultaService.getConsultasByCita(id);
        return ResponseEntity.ok(consultaByCita);
    }
}