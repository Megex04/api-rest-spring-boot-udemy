package com.pe.lacunza.hateoas.controller;

import com.pe.lacunza.hateoas.model.Cuenta;
import com.pe.lacunza.hateoas.model.Monto;
import com.pe.lacunza.hateoas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        }

        for(Cuenta cuenta : listaCuenta) {
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
        }
        CollectionModel<Cuenta> modelo = CollectionModel.of(listaCuenta);
        modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());
        return new ResponseEntity<>(listaCuenta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id) {
        try {
            Cuenta cuenta = cuentaService.getCuenta(id);
            //hateoas
            cuenta.add(linkTo(
                    methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                    .withSelfRel()
            );
            cuenta.add(linkTo(
                    methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null))
                    .withRel("depositos")
            );
            cuenta.add(linkTo(
                    methodOn(CuentaController.class).listarCuentas()
                ).withRel(IanaLinkRelations.COLLECTION)
            );
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/")
    public ResponseEntity<Cuenta> guardarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaBD = cuentaService.guardar(cuenta);

        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
        cuentaBD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBD.getId(), null)).withRel("depositos"));
        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBD.getId())).toUri()).body(cuentaBD);
    }

    @PutMapping(value = "/")
    public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaBD = cuentaService.guardar(cuenta);

        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
        cuentaBD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBD.getId(), null)).withRel("depositos"));
        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuentaBD, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id) {
        try {
            cuentaService.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<Cuenta> depositarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
        Cuenta cuentaBD = cuentaService.depositar(monto.getMonto(), id);

        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBD.getId())).withSelfRel());
        cuentaBD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBD.getId(), null)).withRel("depositos"));
        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuentaBD, HttpStatus.OK);
    }
    @PatchMapping("/{id}/retiro")
    public ResponseEntity<Cuenta> retirarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
        Cuenta cuentaBD = cuentaService.retirar(monto.getMonto(), id);

        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBD.getId())).withSelfRel());
        cuentaBD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBD.getId(), null)).withRel("depositos"));
        cuentaBD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBD.getId(), null)).withRel("retiros"));
        cuentaBD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuentaBD, HttpStatus.OK);
    }
}
