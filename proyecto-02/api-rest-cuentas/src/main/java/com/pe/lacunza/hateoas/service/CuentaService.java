package com.pe.lacunza.hateoas.service;

import com.pe.lacunza.hateoas.exception.CuentaNotFoundException;
import com.pe.lacunza.hateoas.model.Cuenta;
import com.pe.lacunza.hateoas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> listAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuenta(Integer id) {
        return cuentaRepository.findById(id).get();
    }

    public Cuenta guardar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminar(Integer id) throws CuentaNotFoundException {
        if(!cuentaRepository.existsById(id)) {
            throw new CuentaNotFoundException("Cuenta no encontrada con el ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    public Cuenta  depositar(float monto, Integer id) {
        cuentaRepository.actualizarMonto(monto, id);
        return cuentaRepository.findById(id).get();
    }
    public Cuenta  retirar(float monto, Integer id) {
        cuentaRepository.actualizarMonto(-monto, id);
        return cuentaRepository.findById(id).get();
    }
}
