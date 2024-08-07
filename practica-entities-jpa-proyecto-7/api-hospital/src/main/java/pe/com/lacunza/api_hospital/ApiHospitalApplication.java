package pe.com.lacunza.api_hospital;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.com.lacunza.api_hospital.model.*;
import pe.com.lacunza.api_hospital.repository.CitaRepository;
import pe.com.lacunza.api_hospital.repository.ConsultaRepository;
import pe.com.lacunza.api_hospital.repository.MedicoRepository;
import pe.com.lacunza.api_hospital.repository.PacienteRepository;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiHospitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner start(PacienteRepository pacienteRepository, MedicoRepository medicoRepository, CitaRepository citaRepository, ConsultaRepository consultaRepository) {
		return args -> {
			Stream.of("Miguel", "Raul", "Lanudo")
					.forEach(nombre -> {
						Paciente paciente = new Paciente();
						paciente.setNombre(nombre);
						paciente.setFechaNacimiento(new Date());
						paciente.setEnfermedad(false);
						pacienteRepository.save(paciente);
					});
			Stream.of("Babage", "Michael", "Kilombo")
					.forEach(nombre -> {
						Medico medico =  new Medico();
						medico.setNombre(nombre);
						medico.setEmail(nombre.toLowerCase() + ((int) (Math.random() * 10)) + "@gmail.com");
						medico.setEspecialidad(Math.random() > 0.5 ? "Cardiologia" : "Obstreticia");
						medicoRepository.save(medico);
					});
			Paciente pacienteUno = pacienteRepository.findById(1L).orElse(null);
			Medico medicoUno = medicoRepository.findByNombre("Babage");

			Cita cita1 = new Cita();
			cita1.setFecha(new Date());
			cita1.setStatusCita(StatusCita.PENDIENTE);
			cita1.setMedico(medicoUno);
			cita1.setPaciente(pacienteUno);
			citaRepository.save(cita1);

			Cita citaBD1 = citaRepository.findById(1L).orElse(null);

			Consulta consulta = new Consulta();
			consulta.setFechaConsulta(new Date());
			consulta.setCita(citaBD1);
			consulta.setInforme("Informe critico de paciente !!");
			consultaRepository.save(consulta);
		};
	}
}
