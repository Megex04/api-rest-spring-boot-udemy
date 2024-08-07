package pe.com.lacunza.api_hospital.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.Medico;
import pe.com.lacunza.api_hospital.model.Paciente;
import pe.com.lacunza.api_hospital.model.StatusCita;
import pe.com.lacunza.api_hospital.repository.MedicoRepository;
import pe.com.lacunza.api_hospital.repository.PacienteRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CitaMapper {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public CitaDTO toDTO(Cita cita) {
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setId(cita.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedFecha = sdf.format(cita.getFecha());

        citaDTO.setFecha(formatedFecha);
        citaDTO.setCancelado(cita.isCancelado());
        citaDTO.setStatusCita(cita.getStatusCita().name());
        citaDTO.setPacienteId(cita.getPaciente().getId());
        citaDTO.setMedicoId(cita.getMedico().getId());
        return citaDTO;
    }

    public Cita toEntity(CitaDTO citaDTO, Paciente paciente, Medico medico) throws ParseException {
        Cita cita = new Cita();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());
        cita.setFecha(fecha);

        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        return cita;
    }

    public Cita toEntity(CitaDTO citaDTO) throws ParseException {
        Cita cita = new Cita();

        cita.setId(citaDTO.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());
        cita.setFecha(fecha);

        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

        Optional<Paciente> paciente = pacienteRepository.findById(citaDTO.getPacienteId());
        Paciente pacienteBD = paciente.get();
        cita.setPaciente(pacienteBD);

        Optional<Medico> medico = medicoRepository.findById(citaDTO.getMedicoId());
        Medico medicoBD = medico.get();
        cita.setMedico(medicoBD);

        return cita;
    }
}