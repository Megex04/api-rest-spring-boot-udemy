package pe.com.lacunza.pruebas_unitarias.utilities;

import pe.com.lacunza.pruebas_unitarias.entity.Paciente;

public class TestUtilPaciente {

    Paciente paciente_001 = new Paciente(1L, "Jorge Henderson", (short) 23, "mimi01@gmail.com");
    Paciente paciente_002 = new Paciente(10L, "Maria Rodriguez", (short) 20, "mimi02@gmail.com");
    Paciente paciente_003 = new Paciente(20L, "Lalo Pelaez", (short) 32, "mimi03@gmail.com");

    public Paciente generarUnPaciente() {
        return paciente_001;
    }
    public Paciente generarDosPaciente() {
        return paciente_002;
    }
    public Paciente generarTresPaciente() {
        return paciente_003;
    }
}
