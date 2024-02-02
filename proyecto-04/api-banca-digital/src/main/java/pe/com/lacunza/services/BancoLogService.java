package pe.com.lacunza.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lacunza.entities.CuentaActual;
import pe.com.lacunza.entities.CuentaAhorro;
import pe.com.lacunza.entities.CuentaBancaria;
import pe.com.lacunza.repository.CuentaBancariaRepository;

@Service
@Transactional
@Slf4j
public class BancoLogService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar(){
        CuentaBancaria cuentaBancariaBD = cuentaBancariaRepository.findById("2bfab240-1e80-4153-93fe-b7bb060e6bb3").orElse(null);

        if(cuentaBancariaBD != null){
            log.info("**********************************************");
            log.info("Id:" + cuentaBancariaBD.getId());
            log.info("Balance de la cuenta:" + cuentaBancariaBD.getBalance());
            log.info("Estado:" + cuentaBancariaBD.getEstadoCuenta());
            log.info("Fecha de creacion:" + cuentaBancariaBD.getFechaCreacion());
            log.info("Cliente:" + cuentaBancariaBD.getCliente().getNombre());
            log.info("Nombre de la clase - " + cuentaBancariaBD.getClass().getSimpleName());

            if(cuentaBancariaBD instanceof CuentaActual){
                log.info("Sobregiro: " + ((CuentaActual) cuentaBancariaBD).getSobregiro());
            } else if (cuentaBancariaBD instanceof CuentaAhorro) {
                log.info("Tasa de interes: " + ((CuentaAhorro) cuentaBancariaBD).getTasaDeInteres());
            }
            cuentaBancariaBD.getOperacionesCuenta().forEach(operacionCuenta -> {
                log.info("---------------------------------------------");
                log.info("Tipo de operacion:" + operacionCuenta.getTipoOperacion());
                log.info("Fecha:" + operacionCuenta.getFechaOperacion());
                log.info("Monto:" + operacionCuenta.getMonto());
            });
        }
    }
}
