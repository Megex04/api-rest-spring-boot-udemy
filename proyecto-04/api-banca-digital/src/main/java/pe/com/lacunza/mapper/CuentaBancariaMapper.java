package pe.com.lacunza.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.dto.CuentaActualDTO;
import pe.com.lacunza.dto.CuentaAhorroDTO;
import pe.com.lacunza.dto.OperacionCuentaDTO;
import pe.com.lacunza.entities.Cliente;
import pe.com.lacunza.entities.CuentaActual;
import pe.com.lacunza.entities.CuentaAhorro;
import pe.com.lacunza.entities.OperacionCuenta;

@Service
public class CuentaBancariaMapper {

    public ClienteDTO mapperClienteToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }
    public Cliente mapperClienteToModel(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return cliente;
    }
    public CuentaAhorroDTO mapperCAhToDTO(CuentaAhorro cuentaAhorro) {
        CuentaAhorroDTO cuentaAhorroDTO = new CuentaAhorroDTO();
        BeanUtils.copyProperties(cuentaAhorro, cuentaAhorroDTO);
        cuentaAhorroDTO.setClienteDTO(mapperClienteToDTO(cuentaAhorro.getCliente()));
        cuentaAhorroDTO.setTipo(cuentaAhorro.getClass().getSimpleName());
        return cuentaAhorroDTO;
    }
    public CuentaAhorro mapperCAhToModel(CuentaAhorroDTO cuentaAhorroDTO) {
        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        BeanUtils.copyProperties(cuentaAhorroDTO, cuentaAhorro);
        cuentaAhorro.setCliente(mapperClienteToModel(cuentaAhorroDTO.getClienteDTO()));
        return cuentaAhorro;
    }
    public CuentaActualDTO mapperCAcToDTO(CuentaActual cuentaActual) {
        CuentaActualDTO cuentaActualDTO = new CuentaActualDTO();
        BeanUtils.copyProperties(cuentaActual, cuentaActualDTO);
        cuentaActualDTO.setClienteDTO(mapperClienteToDTO(cuentaActual.getCliente()));
        cuentaActualDTO.setTipo(cuentaActual.getClass().getSimpleName());
        return cuentaActualDTO;
    }
    public CuentaActual mapperCAcToModel(CuentaActualDTO cuentaActualDTO) {
        CuentaActual cuentaActual = new CuentaActual();
        BeanUtils.copyProperties(cuentaActualDTO, cuentaActual);
        cuentaActual.setCliente(mapperClienteToModel(cuentaActualDTO.getClienteDTO()));
        return cuentaActual;
    }
    public OperacionCuentaDTO mapperOperacionToDTO(OperacionCuenta operacionCuenta) {
        OperacionCuentaDTO operacionCuentaDTO = new OperacionCuentaDTO();
        BeanUtils.copyProperties(operacionCuenta, operacionCuentaDTO);
        return operacionCuentaDTO;
    }
}
