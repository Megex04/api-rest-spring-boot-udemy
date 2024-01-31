package pe.com.lacunza.services;

import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.entities.Cliente;
import pe.com.lacunza.entities.CuentaActual;
import pe.com.lacunza.entities.CuentaAhorro;
import pe.com.lacunza.entities.CuentaBancaria;
import pe.com.lacunza.exceptions.BalanceInsuficienteException;
import pe.com.lacunza.exceptions.ClienteNotFoundException;
import pe.com.lacunza.exceptions.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    Cliente saveCliente(Cliente cliente);

    CuentaActual saveBancariaCuentaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorro saveBancariaCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException;

    List<ClienteDTO> listClientes();

    CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    List<CuentaBancaria> listCuentasBancarias();
}
