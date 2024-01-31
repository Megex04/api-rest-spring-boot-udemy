package pe.com.lacunza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes() {
        return cuentaBancariaService.listClientes();
    }
}
