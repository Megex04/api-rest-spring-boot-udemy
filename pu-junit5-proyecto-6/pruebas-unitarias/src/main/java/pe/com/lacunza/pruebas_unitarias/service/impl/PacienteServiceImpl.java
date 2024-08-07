package pe.com.lacunza.pruebas_unitarias.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.lacunza.pruebas_unitarias.entity.Paciente;
import pe.com.lacunza.pruebas_unitarias.repository.PacienteRepository;
import pe.com.lacunza.pruebas_unitarias.service.PacienteService;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente createPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> getPacienteById(Long pacienteId) {
        return pacienteRepository.findById(pacienteId);
    }

    @Override
    public Paciente updatePaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void deletePaciente(Long pacienteId) {
        pacienteRepository.deleteById(pacienteId);
    }
}
