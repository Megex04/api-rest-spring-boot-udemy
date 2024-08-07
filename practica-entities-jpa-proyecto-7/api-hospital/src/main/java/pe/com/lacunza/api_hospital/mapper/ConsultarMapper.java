package pe.com.lacunza.api_hospital.mapper;

import org.springframework.stereotype.Component;
import pe.com.lacunza.api_hospital.dto.CitaDTO;
import pe.com.lacunza.api_hospital.dto.ConsultaDTO;
import pe.com.lacunza.api_hospital.model.Cita;
import pe.com.lacunza.api_hospital.model.Consulta;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ConsultarMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConsultaDTO toConsultaDTO(Consulta consulta) {
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(dateFormat.format(consulta.getFechaConsulta()));
        consultaDTO.setInforme(consulta.getInforme());
        if(consulta.getCita() != null) {
            Cita cita = consulta.getCita();
            CitaDTO citaDTO = new CitaDTO();

            citaDTO.setId(cita.getId());
            citaDTO.setFecha(dateFormat.format(cita.getFecha()));
            citaDTO.setCancelado(cita.isCancelado());
            citaDTO.setStatusCita(cita.getStatusCita().toString());
            citaDTO.setPacienteId(cita.getPaciente().getId());
            citaDTO.setMedicoId(cita.getMedico().getId());
            consultaDTO.setCitaDTO(citaDTO);
        }
        return consultaDTO;
    }
    public Consulta toEntity(ConsultaDTO consultaDTO) throws ParseException {
        Consulta consulta = new Consulta();
        consulta.setId(consultaDTO.getId());
        consulta.setFechaConsulta(dateFormat.parse(consultaDTO.getFechaConsulta()));
        consulta.setInforme(consultaDTO.getInforme());
        if(consultaDTO.getCitaDTO() != null) {
            CitaDTO citaDTO = consultaDTO.getCitaDTO();
            Cita cita = new Cita();
            cita.setId(citaDTO.getId());
            consulta.setCita(cita);
        }
        return consulta;
    }
}
