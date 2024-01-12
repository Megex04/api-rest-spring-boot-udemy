package pe.com.lacunza.encuestas.dto;

import lombok.Data;

@Data
public class ValidationError {

    private String code;
    private String message;
}
