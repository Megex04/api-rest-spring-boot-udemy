package pe.com.lacunza.pruebas_unitarias.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pe.com.lacunza.pruebas_unitarias.entity.Paciente;
import pe.com.lacunza.pruebas_unitarias.exception.InvalidRequestException;
import pe.com.lacunza.pruebas_unitarias.exception.NotFoundException;
import pe.com.lacunza.pruebas_unitarias.service.PacienteService;
import pe.com.lacunza.pruebas_unitarias.utilities.TestUtilPaciente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private TestUtilPaciente testUtilPaciente;

    @MockBean
    private PacienteService pacienteService;


    @Test
    @DisplayName("Listado de pacientes \uD83E\uDD29")
    void testListarPacientes() throws Exception {
        List<Paciente> pacientes = new ArrayList<>(Arrays.asList(testUtilPaciente.generarUnPaciente(), testUtilPaciente.generarDosPaciente(), testUtilPaciente.generarTresPaciente()));
        Mockito.when(pacienteService.getAllPacientes()).thenReturn(pacientes);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/pacientes")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].correo", is("mimi01@gmail.com")))
                .andExpect(jsonPath("$[2].nombre", is("Lalo Pelaez")));
    }

    @Test
    @DisplayName("Buscar un paciente \uD83E\uDD2A")
    void buscarPacientePorId() throws Exception {
        Mockito.when(pacienteService.getPacienteById(testUtilPaciente.generarTresPaciente().getPacienteId()))
                .thenReturn(Optional.of(testUtilPaciente.generarTresPaciente()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/pacientes/20")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nombre", is("Lalo Pelaez")));
    }

    @Test
    @DisplayName("Guardar paciente \uD83D\uDE39")
    void testGuardarPaciente() throws Exception {
        Paciente paciente = Paciente.builder()
                .pacienteId(40L)
                .nombre("Olga Fernandez")
                .edad((short) 98)
                .correo("mimi05@gmail.com")
                .build();
        Mockito.when(pacienteService.createPaciente(paciente)).thenReturn(paciente);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/pacientes/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(paciente));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nombre", is("Olga Fernandez")));
    }

    @Test
    @DisplayName("Actualizar paciente (Éxito) \uD83D\uDE07")
    void testActualizarPacienteConExito() throws Exception {
        Paciente pacienteUpdate = Paciente.builder()
                .pacienteId(10L)
                .nombre("Mercedes Ramirez")
                .edad((short) 38)
                .correo("qaweb@gmail.com")
                .build();
        Mockito.when(pacienteService.getPacienteById(testUtilPaciente.generarDosPaciente().getPacienteId())).thenReturn(Optional.of(pacienteUpdate));
        Mockito.when(pacienteService.updatePaciente(pacienteUpdate)).thenReturn(pacienteUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/pacientes/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pacienteUpdate));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nombre", is("Mercedes Ramirez")));
    }

    @Test
    @DisplayName("Actualizar paciente (no encontrado) \uD83C\uDFED")
    void testActualizarPacienteNoEncontrado() throws Exception {
        Paciente pacienteUpdate = Paciente.builder()
                .pacienteId(66L)
                .nombre("Mercedes Ramirez")
                .edad((short) 38)
                .correo("qaweb@gmail.com")
                .build();
        Mockito.when(pacienteService.getPacienteById(testUtilPaciente.generarDosPaciente().getPacienteId())).thenReturn(Optional.of(pacienteUpdate));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/pacientes/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pacienteUpdate));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof NotFoundException
                ))
                .andExpect(result -> assertEquals("Paciente con Id: " + pacienteUpdate.getPacienteId() + " no existe", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Actualizar paciente (con Id NULL) \uD83D\uDE0C")
    void testActualizarPacienteIdNull() throws Exception {
        Paciente pacienteUpdate = Paciente.builder()
                .pacienteId(null)
                .nombre("Mercedes Ramirez")
                .edad((short) 38)
                .correo("qaweb@gmail.com")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/pacientes/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pacienteUpdate));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof InvalidRequestException
                ))
                .andExpect(result -> assertEquals("Los datos del paciente no pueden ser nulos", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Eliminar paciente con exito")
    void testEliminarPacienteConExito() throws Exception {
        Mockito.when(pacienteService.getPacienteById(testUtilPaciente.generarTresPaciente().getPacienteId())).thenReturn(Optional.of(testUtilPaciente.generarTresPaciente()));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/pacientes?pacienteId=20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Eliminar paciente no encontrado")
    void testEliminarPacienteNoEncontrado() throws Exception {
        Mockito.when(pacienteService.getPacienteById(10L)).thenReturn(Optional.of(testUtilPaciente.generarTresPaciente()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/pacientes?pacienteId=20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(NotFoundException.class, result.getResolvedException()));
    }
}