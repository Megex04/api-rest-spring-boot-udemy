package pe.com.lacunza.api_hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.MedicoDTO;
import pe.com.lacunza.api_hospital.dto.PacienteDTO;
import pe.com.lacunza.api_hospital.mapper.CitaMapper;
import pe.com.lacunza.api_hospital.mapper.MedicoMapper;
import pe.com.lacunza.api_hospital.mapper.PacienteMapper;
import pe.com.lacunza.api_hospital.model.*;
import pe.com.lacunza.api_hospital.repository.CitaRepository;
import pe.com.lacunza.api_hospital.repository.ConsultaRepository;
import pe.com.lacunza.api_hospital.repository.MedicoRepository;
import pe.com.lacunza.api_hospital.repository.PacienteRepository;
import pe.com.lacunza.api_hospital.service.CitaService;
import pe.com.lacunza.api_hospital.service.MedicoService;
import pe.com.lacunza.api_hospital.service.PacienteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    //repositories
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    //mappers
    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    //services
    @Autowired
    @Lazy
    private MedicoService medicoService;

    @Autowired
    @Lazy
    private PacienteService pacienteService;

    @Override
    public List<CitaDTO> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaDTO> getCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        return citaOptional.map(citaMapper::toDTO);
    }

    @Override
    public Cita createCita(CitaDTO citaDTO, Long idPaciente, Long idMedico) throws ParseException {
        PacienteDTO pacienteDTO = pacienteService.getPacienteById(idPaciente).orElse(null);
        MedicoDTO medicoDTO = medicoService.getMedicoById(idMedico).orElse(null);

        if(pacienteDTO == null || medicoDTO == null) {
            return null;
        }
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        Medico medico = medicoMapper.toEntity(medicoDTO);
        Cita cita = citaMapper.toEntity(citaDTO, paciente, medico);

        return citaRepository.save(cita);
    }

    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if(citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            Date fecha = new Date(); // AÑADIDO
            SimpleDateFormat dateFormat = null;
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (citaDTO.getFecha() != null) {
                fecha = dateFormat.parse(citaDTO.getFecha());
            }
            cita.setFecha(fecha); // AÑADIDO
            cita.setCancelado(citaDTO.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

            Optional<Paciente> pacienteOptional = pacienteRepository.findById(citaDTO.getPacienteId());
            cita.setPaciente(pacienteOptional.get());

            Optional<Medico> medicoOptional = medicoRepository.findById(citaDTO.getMedicoId());
            cita.setMedico(medicoOptional.get());

            return citaMapper.toDTO(citaRepository.save(cita));
        }
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if(citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            if(cita.getConsulta() != null) {
                Consulta consulta = cita.getConsulta();
                consulta.setCita(null);
                consultaRepository.delete(consulta);
            }
            citaRepository.delete(cita);
        }
    }

    @Override
    public List<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByMedicoId(Long medicoId) {
        List<Cita> citas = citaRepository.findByMedicoId(medicoId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByStatusCita(StatusCita statusCita) {
        List<Cita> citas = citaRepository.findByStatusCita(statusCita);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }
}