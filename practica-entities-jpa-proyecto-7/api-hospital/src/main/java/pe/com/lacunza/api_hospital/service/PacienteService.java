package pe.com.lacunza.api_hospital.service;

import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.PacienteDTO;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<PacienteDTO> getAllPacientes();

    Optional<PacienteDTO> getPacienteById(Long id);

    PacienteDTO createPaciente(PacienteDTO pacienteDTO) throws ParseException;

    PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) throws ParseException;

    void deletePaciente(Long id);

    Collection<CitaDTO> getCitasByPacienteId(Long pacienteId);
}