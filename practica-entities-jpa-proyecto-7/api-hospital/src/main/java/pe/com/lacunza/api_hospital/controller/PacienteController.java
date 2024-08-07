package pe.com.lacunza.api_hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.PacienteDTO;
import pe.com.lacunza.api_hospital.service.PacienteService;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<PacienteDTO>> listarPaciente() {
        List<PacienteDTO> pacientes = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable(value = "id") Long id) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> new ResponseEntity<>(paciente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping(value = {"/", ""})
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody PacienteDTO pacienteDTO) throws ParseException {
        PacienteDTO createdPacienteDTO = pacienteService.createPaciente(pacienteDTO);
        return new ResponseEntity<>(createdPacienteDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable(value = "id") Long id, @RequestBody PacienteDTO pacienteDTO) throws ParseException {
        PacienteDTO updatedPacienteDTO = pacienteService.updatePaciente(id, pacienteDTO);
        if(updatedPacienteDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedPacienteDTO, HttpStatus.OK);
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> borrarPaciente(@PathVariable(value = "id") Long id) {
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{id}/citas")
    public ResponseEntity<Collection<CitaDTO>> citasPorPacienteId(@PathVariable(value = "id") Long pacienteId) {
        Collection<CitaDTO> citas = pacienteService.getCitasByPacienteId(pacienteId);
        if(!citas.isEmpty()) {
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}