package pe.com.lacunza.service.impl;

import pe.com.lacunza.service.ServicioA;

public class ServicioAImpl implements ServicioA {

    @Override
    public int sumar(int a, int b) {
        return a + b + 1;
    }
}
