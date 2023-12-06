package com.pe.lacunza.hateoas.service;

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

    public void eliminar(Integer id) {
        cuentaRepository.deleteById(id);
    }
}
