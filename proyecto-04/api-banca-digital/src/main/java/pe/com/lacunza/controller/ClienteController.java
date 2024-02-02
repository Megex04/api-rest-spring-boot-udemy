package pe.com.lacunza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.exceptions.ClienteNotFoundException;
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

    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosCliente(@PathVariable(value = "id") Long clienteId) throws ClienteNotFoundException {
        return cuentaBancariaService.getCliente(clienteId);
    }
    @PostMapping("/clientes")
    public ClienteDTO registrarCliente(@RequestBody ClienteDTO clienteDTO) {
        return cuentaBancariaService.saveCliente(clienteDTO);
    }
    @PutMapping("/clientes")
    public ClienteDTO actualizarCliente(@RequestBody ClienteDTO clienteDTO) throws ClienteNotFoundException {
        return cuentaBancariaService.updateCliente(clienteDTO);
    }
    @DeleteMapping("/clientes/{id}")
    public void eliminarCliente(@PathVariable(value = "id") Long clienteId) throws ClienteNotFoundException {
        cuentaBancariaService.eliminarCliente(clienteId);
    }
}
