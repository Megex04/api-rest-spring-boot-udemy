package pe.com.lacunza;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.com.lacunza.entities.Cliente;
import pe.com.lacunza.entities.CuentaBancaria;
import pe.com.lacunza.services.BancoService;
import pe.com.lacunza.services.CuentaBancariaService;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner startServiceLOG(BancoService bancoService){
		return args -> {
			bancoService.consultar();
		};

	}

//	@Bean
	CommandLineRunner start(CuentaBancariaService cuentaBancariaService){
		return args -> {
			Stream.of("Chistian","Julen","Cojudo","Luis","Sandra").forEach(nombre -> {
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre.toLowerCase() + "@gmail.com");
				cuentaBancariaService.saveCliente(cliente);
			});
			cuentaBancariaService.listClientes().forEach(cliente -> {
				try {
					cuentaBancariaService.saveBancariaCuentaActual(Math.random() * 90000, 9000, cliente.getId());
					cuentaBancariaService.saveBancariaCuentaAhorro(120000, 5.5, cliente.getId());

					List<CuentaBancaria> cuentasBancarias = cuentaBancariaService.listCuentasBancarias();
					for(CuentaBancaria cuentaBancaria : cuentasBancarias) {
						for (int i = 0; i < 10; i++) {
							cuentaBancariaService.credit(cuentaBancaria.getId(), 10000 + Math.random() * 120000, "Credito");
							cuentaBancariaService.debit(cuentaBancaria.getId(), 1000 + Math.random() * 9000, "Debito");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}

}
