package pe.com.lacunza.pruebas_unitarias.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.pruebas_unitarias.entity.Paciente;
import pe.com.lacunza.pruebas_unitarias.exception.InvalidRequestException;
import pe.com.lacunza.pruebas_unitarias.exception.NotFoundException;
import pe.com.lacunza.pruebas_unitarias.service.PacienteService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping(value = {"/", ""})
    public List<Paciente> listarPaciente() {
        return pacienteService.getAllPacientes();
    }

    @GetMapping(value = "/{pacienteId}")
    public Paciente buscarPacientePorId(@PathVariable(value = "pacienteId") Long pacienteId) {
        return pacienteService.getPacienteById(pacienteId).orElse(null);
    }

    @PostMapping(value = "/")
    public Paciente guardarPaciente(@RequestBody @Valid Paciente paciente) {
        return pacienteService.createPaciente(paciente);
    }

    @PutMapping(value = "/")
    public Paciente actualizarPaciente(@RequestBody Paciente paciente) throws InvalidRequestException {
        if(Objects.isNull(paciente) || Objects.isNull(paciente.getPacienteId())) {
            throw new InvalidRequestException("Los datos del paciente no pueden ser nulos");
        }

        Optional<Paciente> pacienteOptional = pacienteService.getPacienteById(paciente.getPacienteId());
        if(pacienteOptional.isEmpty()){
            throw new NotFoundException("Paciente con Id: " + paciente.getPacienteId() + " no existe");
        }
        Paciente pacienteExiste = pacienteOptional.get();
        pacienteExiste.setNombre(paciente.getNombre());
        pacienteExiste.setEdad(paciente.getEdad());
        pacienteExiste.setCorreo(paciente.getCorreo());

        return pacienteService.updatePaciente(pacienteExiste);
    }

    @DeleteMapping(value = {"/", ""})
    public void eliminarPaciente(@RequestParam(value = "pacienteId") Long pacienteId) {
        if(pacienteService.getPacienteById(pacienteId).isEmpty()) {
            throw new NotFoundException("Paciente con Id: " + pacienteId + " no existe");
        }
        pacienteService.deletePaciente(pacienteId);
    }
}