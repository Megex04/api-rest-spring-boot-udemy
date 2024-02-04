package pe.com.lacunza.services;

import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.dto.CuentaActualDTO;
import pe.com.lacunza.dto.CuentaAhorroDTO;
import pe.com.lacunza.dto.CuentaBancariaDTO;
import pe.com.lacunza.entities.Cliente;
import pe.com.lacunza.entities.CuentaActual;
import pe.com.lacunza.entities.CuentaAhorro;
import pe.com.lacunza.entities.CuentaBancaria;
import pe.com.lacunza.exceptions.BalanceInsuficienteException;
import pe.com.lacunza.exceptions.ClienteNotFoundException;
import pe.com.lacunza.exceptions.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    ClienteDTO saveCliente(ClienteDTO clienteDTO);
    ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException;
    ClienteDTO updateCliente(ClienteDTO clienteDTO) throws ClienteNotFoundException;
    void eliminarCliente(Long clienteId) throws ClienteNotFoundException;

    CuentaActualDTO saveBancariaCuentaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorroDTO saveBancariaCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException;

    List<ClienteDTO> listClientes();

    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    List<CuentaBancariaDTO> listCuentasBancarias();
}
