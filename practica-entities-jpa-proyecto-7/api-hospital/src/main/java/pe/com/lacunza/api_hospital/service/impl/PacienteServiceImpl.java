package pe.com.lacunza.api_hospital.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.PacienteDTO;
import pe.com.lacunza.api_hospital.mapper.CitaMapper;
import pe.com.lacunza.api_hospital.mapper.PacienteMapper;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.Paciente;
import pe.com.lacunza.api_hospital.repository.PacienteRepository;
import pe.com.lacunza.api_hospital.service.CitaService;
import pe.com.lacunza.api_hospital.service.PacienteService;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<PacienteDTO> getAllPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDTO> getPacienteById(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        return optionalPaciente.map(pacienteMapper::toDTO);
    }

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) throws ParseException {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) throws ParseException {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
            paciente.setEnfermedad(pacienteDTO.isEnfermedad());

            paciente = pacienteRepository.save(paciente);
            return pacienteMapper.toDTO(paciente);
        }
        return null;
    }

    @Override
    public void deletePaciente(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();

            for(Cita cita : paciente.getCitas()) {
                citaService.deleteCita(cita.getId());
            }
            pacienteRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(pacienteId);
        return optionalPaciente.map(paciente -> paciente.getCitas().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);
    }
}
