package pe.com.lacunza.service.impl;

import pe.com.lacunza.service.ServiceB;
import pe.com.lacunza.service.ServicioA;

public class ServicioBImpl implements ServiceB {

    private ServicioA servicioA;

    @Override
    public ServicioA getServicioA() {
        return servicioA;
    }

    @Override
    public void setServicioA(ServicioA servicioA) {
        this.servicioA = servicioA;
    }

    @Override
    public int multiplicarSumar(int a, int b, int multiplicador) {
        return servicioA.sumar(a, b) * multiplicador;
    }

    @Override
    public int multiplicar(int a, int b) {
        return a * b;
    }
}
