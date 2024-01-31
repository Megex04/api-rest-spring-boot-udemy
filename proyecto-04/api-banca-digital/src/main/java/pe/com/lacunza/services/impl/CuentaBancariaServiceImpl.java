package pe.com.lacunza.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.entities.*;
import pe.com.lacunza.enums.TipoOperacion;
import pe.com.lacunza.exceptions.BalanceInsuficienteException;
import pe.com.lacunza.exceptions.ClienteNotFoundException;
import pe.com.lacunza.exceptions.CuentaBancariaNotFoundException;
import pe.com.lacunza.mapper.CuentaBancariaMapper;
import pe.com.lacunza.repository.ClienteRepository;
import pe.com.lacunza.repository.CuentaBancariaRepository;
import pe.com.lacunza.repository.OperacionCuentaRepository;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;
    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;
    @Autowired
    private CuentaBancariaMapper cuentaBancariaMapper;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Guardando cliente...");
        return clienteRepository.save(cliente);
    }

    @Override
    public CuentaActual saveBancariaCuentaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado");
        }

        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);
        return cuentaBancariaRepository.save(cuentaActual);
    }

    @Override
    public CuentaAhorro saveBancariaCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado");
        }

        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);
        return cuentaBancariaRepository.save(cuentaAhorro);
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapperToDTO(cliente))
                .collect(Collectors.toList());
        return clienteDTOS;
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        return cuentaBancaria;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);

        if(cuentaBancaria.getBalance() < monto) {
            throw new BalanceInsuficienteException("Balance insuficiente");
        }
        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {

        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario, monto, "Transferencia a: " + cuentaIdDestinatario);
        credit(cuentaIdDestinatario, monto, "Transferencia de: " + cuentaIdPropietario);
    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }
}
