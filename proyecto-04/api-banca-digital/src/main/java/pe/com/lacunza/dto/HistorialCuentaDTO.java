package pe.com.lacunza.dto;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.List;

@Data
public class HistorialCuentaDTO {

    private String id;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperacionCuentaDTO> operacionesCuentaDTOS;
}
