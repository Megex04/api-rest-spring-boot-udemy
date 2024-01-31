package pe.com.lacunza.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.entities.Cliente;

@Service
public class CuentaBancariaMapper {

    public ClienteDTO mapperToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }
    public Cliente mapperToModel(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return cliente;
    }
}
