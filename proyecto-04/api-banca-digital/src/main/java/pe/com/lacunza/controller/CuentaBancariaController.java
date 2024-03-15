package pe.com.lacunza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.lacunza.dto.*;
import pe.com.lacunza.exceptions.BalanceInsuficienteException;
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
    @GetMapping("/detalle/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> listHistorialDeCuenta(@PathVariable(value = "cuentaId") String cuentaId){
        return cuentaBancariaService.listHistorialDeCuenta(cuentaId);
    }
    @GetMapping("/detalle/{cuentaId}/pageOperaciones")
    public HistorialCuentaDTO listHistorialDeCuentaPaginado(@PathVariable(value = "cuentaId") String cuentaId,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "5") int size) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getHistorialCuenta(cuentaId, page, size);
    }
    @PostMapping("/cuentas/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.debit(debitoDTO.getCuentaId(), debitoDTO.getMonto(), debitoDTO.getDescripcion());
        return debitoDTO;
    }
    @PostMapping("/cuentas/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO) throws CuentaBancariaNotFoundException {
        cuentaBancariaService.credit(creditoDTO.getCuentaId(), creditoDTO.getMonto(), creditoDTO.getDescripcion());
        return creditoDTO;
    }
    @PostMapping("/cuentas/transferencias")
    public void realizartransferencia(@RequestBody TransferenciaRequestDTO transferenciaDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.transfer(transferenciaDTO.getCuentaPropietario(), transferenciaDTO.getCuentaDestinatario(), transferenciaDTO.getMonto());
    }
}
