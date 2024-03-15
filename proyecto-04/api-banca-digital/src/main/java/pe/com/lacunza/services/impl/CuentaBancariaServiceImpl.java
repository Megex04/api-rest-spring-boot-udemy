package pe.com.lacunza.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lacunza.dto.*;
import pe.com.lacunza.entities.*;
import pe.com.lacunza.enums.EstadoCuenta;
import pe.com.lacunza.enums.TipoOperacion;
import pe.com.lacunza.exceptions.BalanceInsuficienteException;
import pe.com.lacunza.exceptions.ClienteNotFoundException;
import pe.com.lacunza.exceptions.CuentaBancariaNotFoundException;
import pe.com.lacunza.mapper.CuentaBancariaMapper;
import pe.com.lacunza.repository.ClienteRepository;
import pe.com.lacunza.repository.CuentaBancariaRepository;
import pe.com.lacunza.repository.OperacionCuentaRepository;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.*;
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
    private Random random = new Random();

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = cuentaBancariaMapper.mapperClienteToModel(clienteDTO);
        log.info("Guardando cliente...");
        Cliente savedCliente = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapperClienteToDTO(savedCliente);
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado"));
        return cuentaBancariaMapper.mapperClienteToDTO(cliente);
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) throws ClienteNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteDTO.getId());
        if(clienteOptional.isEmpty()){
            throw new ClienteNotFoundException("El cliente con id " + clienteDTO.getId() + " no encontrado");
        }
        Cliente cliente = clienteRepository.save(cuentaBancariaMapper.mapperClienteToModel(clienteDTO));
        return cuentaBancariaMapper.mapperClienteToDTO(cliente);
    }

    @Override
    public List<ClienteDTO> searchCliente(String keyword) {
        List<Cliente> clientes = clienteRepository.searchClientes(keyword);
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapperClienteToDTO(cliente))
                .collect(Collectors.toList());
        return clientesDTO;
    }

    @Override
    public void eliminarCliente(Long clienteId) throws ClienteNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if(clienteOptional.isEmpty()){
            throw new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado");
        }
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public CuentaActualDTO saveBancariaCuentaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado");
        }

        int index = random.nextInt(3);
        EstadoCuenta[] valuesEC = EstadoCuenta.values();

        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setEstadoCuenta(valuesEC[index]);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);
        CuentaActual savedCuentaActual = cuentaBancariaRepository.save(cuentaActual);
        return cuentaBancariaMapper.mapperCAcToDTO(savedCuentaActual);
    }

    @Override
    public CuentaAhorroDTO saveBancariaCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("El cliente con id " + clienteId + " no encontrado");
        }
        int index = random.nextInt(3);
        EstadoCuenta[] valuesEC = EstadoCuenta.values();

        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setEstadoCuenta(valuesEC[index]);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);
        CuentaAhorro savedCuentaAhorro = cuentaBancariaRepository.save(cuentaAhorro);
        return cuentaBancariaMapper.mapperCAhToDTO(savedCuentaAhorro);
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapperClienteToDTO(cliente))
                .collect(Collectors.toList());
        return clienteDTOS;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        if(cuentaBancaria instanceof CuentaAhorro) {
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
            return cuentaBancariaMapper.mapperCAhToDTO(cuentaAhorro);
        } else {
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
            return cuentaBancariaMapper.mapperCAcToDTO(cuentaActual);

        }
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

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

        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

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
    public List<CuentaBancariaDTO> listCuentasBancarias() {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findAll();
        return cuentasBancarias.stream().map(cuenta -> {
            if(cuenta instanceof CuentaAhorro cuentaAhorro) {
                return cuentaBancariaMapper.mapperCAhToDTO(cuentaAhorro);
            } else {
                CuentaActual cuentaActual = (CuentaActual) cuenta;
                return cuentaBancariaMapper.mapperCAcToDTO(cuentaActual);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<OperacionCuentaDTO> listHistorialDeCuenta(String cuentaId) {
        List<OperacionCuenta> operacionesDeCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId);
        return operacionesDeCuenta.stream().map(operacionCuenta ->
            cuentaBancariaMapper.mapperOperacionToDTO(operacionCuenta)
        ).toList();
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId).orElse(null);

        if(cuentaBancaria == null) {
            throw new CuentaBancariaNotFoundException("Cuenta no encontrada");
        }

        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaIdOrderByFechaOperacionDesc(cuentaId, PageRequest.of(page, size));
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO();
        List<OperacionCuentaDTO> operacionesCuentaDTOS = operacionesCuenta.getContent().stream()
                .map(operacionCuenta -> cuentaBancariaMapper.mapperOperacionToDTO(operacionCuenta)).toList();
        historialCuentaDTO.setOperacionesCuentaDTOS(operacionesCuentaDTOS);
        historialCuentaDTO.setId(cuentaBancaria.getId());
        historialCuentaDTO.setBalance(cuentaBancaria.getBalance());
        historialCuentaDTO.setCurrentPage(page);
        historialCuentaDTO.setPageSize(size);
        historialCuentaDTO.setTotalPages(operacionesCuenta.getTotalPages());
        return historialCuentaDTO;
    }
}
