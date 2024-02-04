package pe.com.lacunza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.lacunza.dto.CuentaBancariaDTO;
import pe.com.lacunza.exceptions.CuentaBancariaNotFoundException;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController{

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/cuenta")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@RequestParam(value = "cuentaId") String idCuenta) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getCuentaBancaria(idCuenta);
    }
    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias(){
        return cuentaBancariaService.listCuentasBancarias();
    }
}
