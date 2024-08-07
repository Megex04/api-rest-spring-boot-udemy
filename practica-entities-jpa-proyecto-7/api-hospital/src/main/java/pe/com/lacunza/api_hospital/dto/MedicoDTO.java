package pe.com.lacunza.api_hospital.dto;

import lombok.Data;

@Data
public class MedicoDTO {

    private Long id;
    private String nombre;
    private String email;
    private String especialidad;
}