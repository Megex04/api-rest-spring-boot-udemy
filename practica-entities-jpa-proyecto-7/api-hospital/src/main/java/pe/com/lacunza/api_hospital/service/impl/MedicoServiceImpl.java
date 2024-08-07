package pe.com.lacunza.api_hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.MedicoDTO;
import pe.com.lacunza.api_hospital.mapper.CitaMapper;
import pe.com.lacunza.api_hospital.mapper.MedicoMapper;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.Medico;
import pe.com.lacunza.api_hospital.repository.MedicoRepository;
import pe.com.lacunza.api_hospital.service.CitaService;
import pe.com.lacunza.api_hospital.service.MedicoService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    @Override
    public List<MedicoDTO> getAllMedicos() {
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MedicoDTO> getMedicoById(Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        return medico.map(medicoMapper::toDTO);
    }

    @Override
    public MedicoDTO createMedico(MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.toEntity(medicoDTO);
        medico = medicoRepository.save(medico);
        return medicoMapper.toDTO(medico);
    }

    @Override
    public MedicoDTO updateMedico(Long id, MedicoDTO medicoDTO) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if(medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();
            medico.setNombre(medicoDTO.getNombre());
            medico.setEmail(medicoDTO.getEmail());
            medico.setEspecialidad(medicoDTO.getEspecialidad());
            medico = medicoRepository.save(medico);
            return medicoMapper.toDTO(medico);
        }
        return null;
    }

    @Override
    public void deleteMedico(Long id) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if(medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();

            for (Cita cita : medico.getCitas()) {
                citaService.deleteCita(cita.getId());
            }
            medicoRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasByMedicoId(Long medicoId) {
        Optional<Medico> optionalMedico = medicoRepository.findById(medicoId);
        return optionalMedico.map(medico -> medico.getCitas().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public List<MedicoDTO> getMedicosByEspecialidad(String especialidad) {
        List<Medico> medicos = medicoRepository.findByEspecialidad(especialidad);
        return medicos.stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }
}