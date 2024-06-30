import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.com.lacunza.service.ServiceB;
import pe.com.lacunza.service.ServicioA;
import pe.com.lacunza.service.impl.ServicioAImpl;
import pe.com.lacunza.service.impl.ServicioBImpl;

public class TestServicioA {

    @Test
    void testSumar() {
        int a = 3, b= 5;
        ServicioA servicioA = new ServicioAImpl();
        //valor real - valor esperado
        Assertions.assertEquals(servicioA.sumar(a, b), 8 + 1);
    }
    @Test
    void testMultiplicar() {
        ServiceB servicioB = new ServicioBImpl();
        Assertions.assertEquals(servicioB.multiplicar(3, 5), 15);
    }
    @Test
    void multilplicarSumar() {
        ServicioA servicioA = Mockito.mock(ServicioA.class);
        Mockito.when(servicioA.sumar(3, 5)).thenReturn(9);

        ServiceB servicioB = new ServicioBImpl();
        servicioB.setServicioA(servicioA);

        Assertions.assertEquals(18, servicioB.multiplicarSumar(3, 5, 2));
    }
}
