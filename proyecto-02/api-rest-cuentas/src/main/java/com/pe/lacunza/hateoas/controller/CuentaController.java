package com.pe.lacunza.hateoas.controller;

import com.pe.lacunza.hateoas.model.Cuenta;
import com.pe.lacunza.hateoas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        List<Cuenta> listaCuenta = cuentaService.listAll();

        if(listaCuenta.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(listaCuenta, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id) {
        try {
            Cuenta cuenta = cuentaService.getCuenta(id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/")
    public ResponseEntity<Cuenta> guardarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaBD = cuentaService.guardar(cuenta);
        return new ResponseEntity<>(cuentaBD, HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaBD = cuentaService.guardar(cuenta);
        return new ResponseEntity<>(cuentaBD, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id) {
        try {
            Cuenta cuenta = cuentaService.getCuenta(id);
            if(cuenta != null) {
                cuentaService.eliminar(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
