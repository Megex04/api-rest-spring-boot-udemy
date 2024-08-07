package pe.com.lacunza.api_hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.mapper.CitaMapper;
import pe.com.lacunza.api_hospital.mapper.MedicoMapper;
import pe.com.lacunza.api_hospital.mapper.PacienteMapper;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.StatusCita;
import pe.com.lacunza.api_hospital.service.CitaService;
import pe.com.lacunza.api_hospital.service.MedicoService;
import pe.com.lacunza.api_hospital.service.PacienteService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<CitaDTO>> listarCitas() {
        List<CitaDTO> citas = citaService.getAllCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CitaDTO> listarCitaPorId(@PathVariable(value = "id") Long id) {
        Optional<CitaDTO> citaDTOOptional = citaService.getCitaById(id);
        return citaDTOOptional.map(citaDTO -> new ResponseEntity<>(citaDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping(value = "/{idPaciente}/{idMedico}")
    public ResponseEntity<CitaDTO> crearCita(@RequestBody CitaDTO citaDTO, @PathVariable(value = "idPaciente") Long idPaciente, @PathVariable(value = "idMedico") Long idMedico) throws ParseException {
        Cita createdCita = citaService.createCita(citaDTO, idPaciente, idMedico);
        if(createdCita == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CitaDTO createdCitaDTO = citaMapper.toDTO(createdCita);
        return new ResponseEntity<>(createdCitaDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable(value = "id") Long id, @RequestBody CitaDTO citaDTO) throws ParseException {
        CitaDTO updatedCitaDTO = citaService.updateCita(id, citaDTO);
        if(updatedCitaDTO != null) {
            return ResponseEntity.ok(updatedCitaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> borrarCita(@PathVariable(value = "id") Long id) {
        citaService.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTO>> citasPorPacienteId(@PathVariable(value = "pacienteId") Long pacienteId) {
        List<CitaDTO> citas = citaService.getCitasByPacienteId(pacienteId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
    @GetMapping(value = "/medico/{medicoId}")
    public ResponseEntity<List<CitaDTO>> citasPorMedicoId(@PathVariable(value = "medicoId") Long medicoId) {
        List<CitaDTO> citas = citaService.getCitasByMedicoId(medicoId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
    @GetMapping(value = "/status/{statusCita}")
    public ResponseEntity<List<CitaDTO>> citasPorStatusCita(@PathVariable(value = "statusCita") StatusCita statusCita) {
        List<CitaDTO> citas = citaService.getCitasByStatusCita(statusCita);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
}