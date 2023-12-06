package com.pe.lacunza.hateoas;

import com.pe.lacunza.hateoas.model.Cuenta;
import com.pe.lacunza.hateoas.repository.CuentaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTests {

    @Autowired
    private CuentaRepository cuentaRepository;


    @Test
    void testGuardarCuenta() {
        Cuenta cuenta = new Cuenta(66, "2345666");
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        Assertions.assertThat(cuentaGuardada).isNotNull(); // comprobar que la cuenta no sea null
        Assertions.assertThat(cuentaGuardada.getId()).isGreaterThan(0); // comprobar que el id de la cuenta sea mayor a 0
    }

}
