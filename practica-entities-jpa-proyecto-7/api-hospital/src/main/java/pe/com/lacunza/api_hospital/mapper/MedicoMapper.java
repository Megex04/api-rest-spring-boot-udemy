package pe.com.lacunza.api_hospital.mapper;

import org.springframework.stereotype.Component;
import pe.com.lacunza.api_hospital.dto.MedicoDTO;
import pe.com.lacunza.api_hospital.model.Medico;

@Component
public class MedicoMapper {

    public MedicoDTO toDTO(Medico medico) {
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setEspecialidad(medico.getEspecialidad());
        return medicoDTO;
    }

    public Medico toEntity(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setId(medicoDTO.getId());
        medico.setNombre(medicoDTO.getNombre());
        medico.setEmail(medicoDTO.getEmail());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medico;
    }
}