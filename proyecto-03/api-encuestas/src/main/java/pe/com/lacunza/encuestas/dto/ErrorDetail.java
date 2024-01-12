package pe.com.lacunza.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private Long timestamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
