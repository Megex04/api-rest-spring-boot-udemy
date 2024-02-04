package pe.com.lacunza;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.com.lacunza.dto.ClienteDTO;
import pe.com.lacunza.dto.CuentaActualDTO;
import pe.com.lacunza.dto.CuentaAhorroDTO;
import pe.com.lacunza.dto.CuentaBancariaDTO;
import pe.com.lacunza.entities.Cliente;
import pe.com.lacunza.entities.CuentaBancaria;
import pe.com.lacunza.services.BancoLogService;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner startServiceLOG(BancoLogService bancoLogService){
		return args -> {
			bancoLogService.consultar();
		};

	}

	@Bean
	CommandLineRunner start(CuentaBancariaService cuentaBancariaService){
		return args -> {
			Stream.of("Chistian","Julen","Cojudo","Luis","Sandra").forEach(nombre -> {
				ClienteDTO cliente = new ClienteDTO();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre.toLowerCase() + "@gmail.com");
				cuentaBancariaService.saveCliente(cliente);
			});
			cuentaBancariaService.listClientes().forEach(cliente -> {
				try {
					cuentaBancariaService.saveBancariaCuentaActual(Math.random() * 90000, 9000, cliente.getId());
					cuentaBancariaService.saveBancariaCuentaAhorro(120000, 5.5, cliente.getId());

					List<CuentaBancariaDTO> cuentasBancarias = cuentaBancariaService.listCuentasBancarias();
					for(CuentaBancariaDTO cuentaBancaria : cuentasBancarias) {
						for (int i = 0; i < 10; i++) {
							String cuentaId;
							if(cuentaBancaria instanceof CuentaAhorroDTO) {
								cuentaId = ((CuentaAhorroDTO) cuentaBancaria).getId();
							} else {
								cuentaId = ((CuentaActualDTO) cuentaBancaria).getId();
							}

							cuentaBancariaService.credit(cuentaId, 10000 + Math.random() * 120000, "Credito");
							cuentaBancariaService.debit(cuentaId, 1000 + Math.random() * 9000, "Debito");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}

}
