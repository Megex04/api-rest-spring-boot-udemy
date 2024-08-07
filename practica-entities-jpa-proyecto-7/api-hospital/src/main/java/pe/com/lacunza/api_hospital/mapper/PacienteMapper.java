package pe.com.lacunza.api_hospital.mapper;

import org.springframework.stereotype.Component;
import pe.com.lacunza.api_hospital.dto.PacienteDTO;
import pe.com.lacunza.api_hospital.model.Paciente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PacienteMapper {
    public PacienteDTO toDTO(Paciente paciente) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateConverted = simpleDateFormat.format(paciente.getFechaNacimiento());
            Date fechaNacimientoDate = simpleDateFormat.parse(dateConverted);

            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(paciente.getId());
            pacienteDTO.setNombre(paciente.getNombre());
            pacienteDTO.setFechaNacimiento(fechaNacimientoDate);
            pacienteDTO.setEnfermedad(paciente.isEnfermedad());
            return pacienteDTO;
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }
    public Paciente toEntity(PacienteDTO pacienteDTO) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateConverted = simpleDateFormat.format(pacienteDTO.getFechaNacimiento());

        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setFechaNacimiento(simpleDateFormat.parse(dateConverted));
        paciente.setEnfermedad(pacienteDTO.isEnfermedad());
        return paciente;
    }
}
