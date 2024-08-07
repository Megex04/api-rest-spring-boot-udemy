package pe.com.lacunza.api_hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.MedicoDTO;
import pe.com.lacunza.api_hospital.service.MedicoService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        List<MedicoDTO> medicos = medicoService.getAllMedicos();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<MedicoDTO> medicoPorId(@PathVariable(value = "id") Long id) {
        Optional<MedicoDTO> medicoDTOOptional = medicoService.getMedicoById(id);
        return medicoDTOOptional.map(medicoDTO -> new ResponseEntity<>(medicoDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping(value = {"/", ""})
    public ResponseEntity<MedicoDTO> crearMedico(@RequestBody MedicoDTO medicoDTO) {
        MedicoDTO createdMedicoDTO = medicoService.createMedico(medicoDTO);
        return new ResponseEntity<>(createdMedicoDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<MedicoDTO> actualizarMedico(@PathVariable(value = "id") Long id, @RequestBody MedicoDTO medicoDTO) throws Exception {
        MedicoDTO updatedMedicoDTO = medicoService.updateMedico(id, medicoDTO);
        if(updatedMedicoDTO != null) {
            return new ResponseEntity<>(updatedMedicoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> borrarMedico(@PathVariable(value = "id") Long id) throws Exception {
        medicoService.deleteMedico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{id}/citas")
    public ResponseEntity<Collection<CitaDTO>> citasPorMedicoId(@PathVariable(value = "id") Long medicoId) {
        Collection<CitaDTO> citas = medicoService.getCitasByMedicoId(medicoId);
        if(citas != null) {
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/especialidad/{especialidad}")
    public ResponseEntity<List<MedicoDTO>> medicosPorEspecialidad(@PathVariable(value = "especialidad") String especialidad) {
        List<MedicoDTO> medicos = medicoService.getMedicosByEspecialidad(especialidad);
        if(medicos != null) {
            return new ResponseEntity<>(medicos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}